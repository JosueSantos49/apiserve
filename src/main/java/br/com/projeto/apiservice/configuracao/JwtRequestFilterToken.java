package br.com.projeto.apiservice.configuracao;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.projeto.apiservice.service.JwtService;
import br.com.projeto.apiservice.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtRequestFilterToken extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private JwtService jwtService;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilterToken.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				
		final String header = request.getHeader("Authorization");
		logger.info("JwtRequestFilterToken doFilterInternal header: {}", header);
		
		String jwtToken = null;
		String usuarioNome = null;		
		
		if(header != null && header.startsWith("Bearer ")) {
			
			jwtToken = header.substring(7);			
			//jwtToken = header.substring(7).trim();
			logger.info("JwtRequestFilterToken doFilterInternal jwtToken: {}", jwtToken);
			
			try {
				
				usuarioNome = jwtUtil.getUsuarioNomeParaTokem(jwtToken);
				logger.info("JwtRequestFilterToken doFilterInternal usuarioNome: {}", usuarioNome);
				
			} catch (IllegalArgumentException e) {
				logger.error("JwtRequestFilterToken - Não foi possível obter o token JWT: {} ", e.getMessage());
			} catch (ExpiredJwtException e) {
				logger.error("JwtRequestFilterToken - O token Jwt expirou.: {} ", e.getMessage());
			}
			
		} else {
			System.out.println("Jwt token não começa com Bearer.");
		}
		
		
		if(usuarioNome != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails  =  jwtService.loadUserByUsername(usuarioNome);
			
			if(jwtUtil.validateToken(jwtToken, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails,
						null,
						userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);				
			}
		}
		
		filterChain.doFilter(request, response);
		
	}
}
