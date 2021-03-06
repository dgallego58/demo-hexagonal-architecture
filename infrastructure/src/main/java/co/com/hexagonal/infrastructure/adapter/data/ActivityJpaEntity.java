package co.com.hexagonal.infrastructure.adapter.data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activities", schema = "hexagon")
public class ActivityJpaEntity {

    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant timestamp;

    @Column
    private Long ownerAccountId;

    @Column
    private Long sourceAccountId;

    @Column
    private Long targetAccountId;

    @Column
    private Long amount;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ActivityJpaEntity that = (ActivityJpaEntity) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 949648061;
    }
}
