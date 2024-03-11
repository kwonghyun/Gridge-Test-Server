package com.example.demo.src.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority implements GrantedAuthority {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", authorityType=" + authorityType +
                '}';
    }
    @Override
    public String getAuthority() {
        return authorityType.name();
    }

    public enum AuthorityType {
        USER, ADMIN;
        @JsonCreator
        public static AuthorityType from(String value) {
            try {
                return AuthorityType.valueOf(value.toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
        }
    }
}
