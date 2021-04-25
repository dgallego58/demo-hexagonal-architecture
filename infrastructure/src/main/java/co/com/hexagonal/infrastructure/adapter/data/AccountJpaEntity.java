package co.com.hexagonal.infrastructure.adapter.data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts", schema = "hexagon")
public class AccountJpaEntity {


    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        AccountJpaEntity that = (AccountJpaEntity) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 494723045;
    }
}
