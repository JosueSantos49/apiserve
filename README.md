# apiserve
Api criada com: 
-> Java 17 
-> Spring boot: v3.1.4
-> Maven
-> JPA
-> MSQL
-> Lombok
  
-> Spring Security com JWT(JSON Web Token) Visão geral:

- O usuário ao fazer login é gerado um token de autenticação. Pela função do Usuário(administrador, usuário), autorizamos o Usuário a acessar os recursos. 
- WebSecurityConfig é o ponto crucial da implementação de segurança. Configura cors, csrf, gerenciamento de sessão, regras para recursos protegidos. 
- UserDetailsService a interface possui um método para carregar o usuário por nome de usuário e retorna um UserDetailsobjeto que o Spring Security pode usar para autenticação e validação.
- UserDetails contém informações necessárias (como: nome de usuário, senha, autoridades) para construir um objeto Authentication.
- UsernamePasswordAuthenticationToken obtém {nome de usuário, senha} da solicitação de login e AuthenticationManager o usará para autenticar uma conta de login.
- AuthenticationManager possui um DaoAuthenticationProvider(com ajuda de UserDetailsService & PasswordEncoder) para validar UsernamePasswordAuthenticationToken o objeto. Se for bem-sucedido, AuthenticationManager retornará um objeto Authentication totalmente preenchido (incluindo autoridades concedidas).
- OncePerRequestFilter faz uma única execução para cada solicitação à nossa API. Ele fornece um doFilterInternal()método no qual implementaremos a análise e validação do JWT, carregando os detalhes do usuário (usando UserDetailsService), verificando a autorização (usando UsernamePasswordAuthenticationToken).
- AuthenticationEntryPoint detectará um erro de autenticação.
- O repositório contém UserRepositorye RoleRepository para trabalhar com o banco de dados, será importado para o Controller.
- O controlador recebe e trata a solicitação depois que ela foi filtrada por OncePerRequestFilter.
- AuthController lida com solicitações de inscrição/login.
- TestController tem acesso a métodos de recursos protegidos com validações baseadas em funções.






