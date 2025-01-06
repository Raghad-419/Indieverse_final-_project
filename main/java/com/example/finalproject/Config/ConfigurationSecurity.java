package com.example.finalproject.Config;

import com.example.finalproject.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {
        private final MyUserDetailsService userDetailsService;
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/developer/register"
                        , "/api/v1/player/register"
                        , "/api/v1/reviewer/register"
                        , "/api/v1/review/get-all-reviews"
                        ,"/api/v1/game/get-all-games"
                        ,"/api/v1/developer/search-my-game/**"
                        ,"/api/v1/image/get-image/**"
                        ,"/api/v1/image/get-videos/**").permitAll()
                .requestMatchers("/api/v1/auth/**"
                        ,"/api/v1/player/get-all-players/**"
                        ,"/api/v1/support/get-all-support").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/analytics/**"
                        ,"/api/v1/developer/update/**"
                        ,"/api/v1/developer/get-all-my-games/**"
                        ,"/api/v1/developer/propose-collaboration/**"
                        ,"/api/v1/developer/get-collaboration-requests/**"
                        ,"/api/v1/developer/accept-collaboration/**"
                        ,"/api/v1/developer/reject-collaboration/**"
                        ,"/api/v1/game/update-game/**"
                        ,"/api/v1/game/add-game/**"
                        ,"/api/v1/game/apply-discount/**"
                        ,"/api/v1/game/remove-discount/**"
                        ,"/api/v1/game/assign-tag/**"
                        ,"/api/v1/game/assign-genre/**"
                        ,"/api/v1/image/upload/**"
                        ,"/api/v1/image/upload-video/**"
                        ,"/api/v1/review/upload-video-review"
                        ,"/api/v1/game/assign-platform/**"
                        ,"/api/v1/game/assign-engine/**").hasAuthority("DEVELOPER")
                .requestMatchers("/api/v1/game/recommend-games/**"
                        ,"/api/v1/player/get-player/**"
                        ,"/api/v1/player/update/**"
                        ,"/api/v1/transaction/buy-game/**"
                        ,"/api/v1/transaction/pre-order/**"
                        ,"/api/v1/wishlist/add-game-to-wishlist/**"
                        ,"/api/v1/wishlist/remove-game-from-wishlist/**",
                        "/api/v1/wishlist/get-wishlist"
                        ,"/api/v1/player/delete/**").hasAuthority("PLAYER")
                .requestMatchers("/api/v1/reviewer/get-reviewer/**"
                        ,"/api/v1/reviewer/update/**",
                        "/api/v1/transaction/review-trial/**").hasAuthority("REVIEWER")
                .requestMatchers("/api/v1/developer/get-top-developer/**"
                        ,"/api/v1/game/get-games-by-badge/**"
                        ,"/api/v1/game/find-similar-games/**"
                        ,"/api/v1/game/get-top-games/**"
                        ,"/api/v1/game/released-in-range"
                        ,"/api/v1/game/recent-release-games/**"
                        ,"/api/v1/reaction/like/**"
                        ,"/api/v1/reaction/dislike/**"
                        ,"/api/v1/review/get-my-reviews/**"
                        ,"/api/v1/review/get-review-by-reviewers/**"
                        ,"/api/v1/review/get-review-by-players/**"
                        , "/api/v1/review/add-review/**"
                        ,"/api/v1/review/update-review/**").hasAnyAuthority("REVIEWER","PLAYER")
                .requestMatchers("/api/v1/developer/get-developer/**"
                        ,"/api/v1/developer/delete/**"
                        ,"/api/v1/game/delete-game/**").hasAnyAuthority("ADMIN","DEVELOPER")
                .requestMatchers("/api/v1/game/filter-games-by-price-range/**"
                        ,"/api/v1/game/find-game-by-developer/**"
                        ,"/api/v1/review/get-review-by-game/**"
                        ,"/api/v1/review/delete-review/**").hasAnyAuthority("REVIEWER","PLAYER","ADMIN")
                .requestMatchers("/api/v1/transaction/get-player-history/**").hasAnyAuthority("ADMIN","PLAYER")
                .requestMatchers("/api/v1/support/send-support-ticket/**").hasAnyAuthority("REVIEWER","PLAYER","DEVELOPER")
                .requestMatchers("/api/v1/reviewer/delete/**").hasAnyAuthority("REVIEWER","ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/logout").permitAll()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();

        return http.build();
    }
}
