package co.com.hexagonal.infrastructure.adapter.data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class AccountJpaEntity {


    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue
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
