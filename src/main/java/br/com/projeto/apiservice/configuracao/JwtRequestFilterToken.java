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

@Component
public class JwtRequestFilterToken extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String header = request.getHeader("Authorization");
		
		System.out.println("header: "+header);
		
		String jwtToken = null;
		String usuarioNome = null;		
		if(header != null && header.startsWith("Bearer ")) {
			
			//jwtToken = header.substring(7);
			jwtToken = header.substring(7).trim();
			
			try {
				
				usuarioNome = jwtUtil.getUsuarioNomeParaTokem(jwtToken);
				
			} catch (IllegalArgumentException e) {
				System.out.println("Não foi possível obter o token JWT." + e);
			} catch (ExpiredJwtException e) {
				System.out.println("O token Jwt expirou. " + e);
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
