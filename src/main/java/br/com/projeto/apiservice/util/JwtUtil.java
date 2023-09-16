package br.com.projeto.apiservice.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;
	
	private static final int TOKEN_VALIDITY = 3600 * 5;
		
	public String getUsuarioNomeParaTokem(String toke) {
		
		logger.info("JwtUtil: {getUsuarioNomeParaTokem}");
		return getClaimParaToken(toke, Claims::getSubject);
		
	}
	
	private <T> T getClaimParaToken(String token, Function<Claims, T> claimResolver) {
		
		final Claims claims = getAllClaimsParaToken(token);
		logger.info("JwtUtil getClaimParaToken: {}", claims);
		
		return claimResolver.apply(claims);
	}
	
	private Claims getAllClaimsParaToken(String token) {	
		
		SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));		
		logger.info("JwtUtil getAllClaimsParaToken secret: {}", secret);
		
		return Jwts.parserBuilder()
				.setSigningKey(secret)
				.build()
				.parseClaimsJws(token)
				//.parseClaimsJws(token.replace("Bearer ", ""))
				//.parseClaimsJws(token.replace("{", "").replace("}",""))
				.getBody();		
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		
		try {
			
			String usuarioNome = getUsuarioNomeParaTokem(token);	
			logger.info("JwtUtil validateToken: {}", usuarioNome);
			
			return ( usuarioNome.equals(userDetails.getUsername()) && !isTokenExpired(token) );	
			
		} catch (Exception e) {
			logger.error("JwtUtil validateToken: {} ", e.getMessage());
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
	
	private Key getSigningKey() {
        byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
        logger.info("JwtUtil getSigningKey() {}", keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
