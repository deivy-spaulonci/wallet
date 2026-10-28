package com.br.wallet.security;

import com.br.wallet.dto.request.update.ChangePasswordRequestDTO;
import com.br.wallet.entity.Usuario;
import com.br.wallet.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    // Chave secreta em Base64 (mínimo de 256 bits para HS256)
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    // Tempo de expiração em milissegundos
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    /**
     * Extrai o subject (normalmente o e-mail/username) contido no token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrai uma claim específica utilizando uma função genérica.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Gera um token simples a partir dos dados do UserDetails.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Gera um token incluindo claims customizadas (ex: ID do usuário, roles, etc.).
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey()) // Assina com a chave privada HMAC
                .compact();
    }

    /**
     * Valida se o token pertence ao usuário e se ainda está no prazo de validade.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        final Date issuedAt = extractClaim(token, Claims::getIssuedAt);

        if (userDetails instanceof Usuario usuario) {
            if (usuario.getPasswordChangedAt() != null) {
                // Converte LocalDateTime para Date para comparar com o JWT
                Date passwordChangeDate = Timestamp.valueOf(usuario.getPasswordChangedAt());

                // Se o token foi gerado ANTES da troca de senha, ele é inválido!
                if (issuedAt.before(passwordChangeDate)) {
                    return false;
                }
            }
        }

        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Valida a assinatura do token e retorna seu payload (Claims).
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey()) // Sintaxe JJWT 0.12.x
                .build()
                .parseSignedClaims(token)
                .getPayload(); // Retorna o corpo das claims
    }

    /**
     * Decodifica a secret em Base64 para gerar o objeto SecretKey seguro.
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //LOGICA PARA TROCAR A SENHA

    public void changePassword(ChangePasswordRequestDTO request, Principal connectedUser) {

        // Obtém o usuário autenticado do SecurityContext
        var user = (Usuario) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // 1. Valida se a senha atual informada confere com o hash no banco
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("A senha atual está incorreta.");
        }

        // 2. Valida se a nova senha e a confirmação são idênticas
        if (!request.newPassword().equals(request.confirmationPassword())) {
            throw new IllegalArgumentException("A nova senha e a confirmação não coincidem.");
        }


        // 3. Codifica a nova senha e salva no banco
        usuarioService.changePassword(request, passwordEncoder.encode(request.newPassword()));
//        user.setPassword(passwordEncoder.encode(request.newPassword()));
//        userRepository.save(user);
    }


}
