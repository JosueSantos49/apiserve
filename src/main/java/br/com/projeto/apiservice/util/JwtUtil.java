package br.com.projeto.apiservice.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.projeto.apiservice.service.UsuarioDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
	@Value("${projeto.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${projeto.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	@Value("${projeto.app.jwtCookieName}")
	private String jwtCookie;
	
	private static final int TOKEN_VALIDITY = 3600 * 5;
	
	public ResponseCookie generateJwtCookie(UsuarioDetailsImpl usuarioPrincipal) {
		String jwt = generateTokenFromUsername(usuarioPrincipal.getUsername());
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
				.path("").maxAge(24 * 60 * 60).httpOnly(true)
				.build();
		
		return cookie;
	}
		
	public String getUsuarioNomeParaTokem(String toke) {
		
		return getClaimParaToken(toke, Claims::getSubject);
		
	}
	
	private <T> T getClaimParaToken(String token, Function<Claims, T> claimResolver) {
		
		final Claims claims = getAllClaimsParaToken(token);
		logger.info("JwtUtil getClaimParaToken: {}", claims);
		
		return claimResolver.apply(claims);
	}
	
	private Claims getAllClaimsParaToken(String token) {	
		
		byte[] keyBytesSecret = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
		
		return Jwts.parserBuilder()
				.setSigningKey(keyBytesSecret)
				.build()
				.parseClaimsJws(token)
				.getBody();		
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		
		try {
			
			String usuarioNome = getUsuarioNomeParaTokem(token);	
			logger.info("JwtUtil validateToken: {}", usuarioNome);
			
			return ( usuarioNome.equals(userDetails.getUsername()) && !isTokenExpired(token) );	
			
		} catch (MalformedJwtException  e) {
			logger.error("Inválida JWT Token: {} ", e.getMessage());
		} catch (ExpiredJwtException e) {
		    logger.error("JWT token expirou: {}", e.getMessage());
	    } catch (UnsupportedJwtException e) {
	    	logger.error("JWT token não é compatível: {}", e.getMessage());
	    } catch (IllegalArgumentException e) {
	    	logger.error("JWT a string de reivindicações está vazia: {}", e.getMessage());
	    }
		
		return false;	
	}
	
	private boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token);
		logger.info("JwtUtil {isTokenExpired}", expirationDate);		
		return expirationDate.before(new Date());
	}
	
	private Date getExpirationDateFromToken(String token) {
		return getClaimParaToken(token, Claims::getExpiration);
	}
	
	//Original
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))				
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();		
	}
	
	public String generateTokenFromUsername(String usuarioNome) {
				
		return Jwts.builder()
				.setSubject(usuarioNome)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))				
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();		
	}
	
	private Key getSigningKey() {
        byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
        logger.info("JwtUtil getSigningKey() {}", keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	
	
}
