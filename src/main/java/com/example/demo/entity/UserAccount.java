@Entity
public class UserAccount {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
