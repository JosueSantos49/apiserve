package br.com.projeto.apiservice.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "learn_programming_yourself";
	
	private static final int TOKEN_VALIDITY = 3600 * 5;
	
	public String getUsuarioNomeParaTokem(String toke) {
		return getClaimParaToken(toke, Claims::getSubject);
	}
	
	private <T> T getClaimParaToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsParaToken(token);
		return claimResolver.apply(claims);
	}
	
	private Claims getAllClaimsParaToken(String token) {		
		//return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		String usuarioNome = getUsuarioNomeParaTokem(token);
		return ( usuarioNome.equals(userDetails.getUsername()) && !isTokenExpired(token) );		
	}
	
	private boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token);
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
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
	
}
