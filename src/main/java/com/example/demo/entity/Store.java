@Entity
public class Store {

    @Id @GeneratedValue
    private Long id;

    private String storeName;
    private String address;
    private String region;

    private boolean active = true;
}
