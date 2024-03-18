package com.example.demo.src.feed.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
@Entity
public class MediaContent extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true, length = 1000)
    private String dir;

    @Column(nullable = false, updatable = false)
    private String originalFileName;

    @Column(nullable = false, updatable = false)
    private Float size;

    @Column(nullable = false, updatable = false)
    private String extension;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        IMAGE("jpg", "jpeg", "png", "gif"),
        VIDEO("mp4", "avi", "mov", "mkv");
        private final String[] extensions;
        Type(String... extensions) {
            this.extensions = extensions;
        }

        public static Type getByExtension(String extension) {
            for (Type type : Type.values()) {
                for (String ext : type.extensions) {
                    if (ext.equalsIgnoreCase(extension)) {
                        return type;
                    }
                }
            }
            return null;
        }
    }
}
