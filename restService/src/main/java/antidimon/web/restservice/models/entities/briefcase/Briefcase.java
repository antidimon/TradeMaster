package antidimon.web.restservice.models.entities.briefcase;


import antidimon.web.restservice.models.entities.user.MyUser;
import antidimon.web.restservice.models.entities.operation.Operation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "briefcases")
@NoArgsConstructor
@Getter
@Setter
public class Briefcase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private MyUser owner;

    @Column(name = "briefcase_name")
    private String briefcaseName = "Test";

    @Column(name = "deposited_balance")
    private double depositedBalance;

    @Column(name = "balance_now")
    private double balanceNow;

    @Column(name = "free_balance")
    private double freeBalance;

    @Column(name = "total_revenue")
    private double totalRevenue;

    @OneToMany(mappedBy = "briefcase", fetch = FetchType.EAGER)
    private List<BriefcaseStock> stocks;

    @OneToMany(mappedBy = "operationBriefcase", fetch = FetchType.LAZY)
    private List<Operation> operations;


    public Briefcase(MyUser owner) {
        this.owner = owner;
    }

    public Briefcase(MyUser owner, String briefcaseName) {
        this.owner = owner;
        this.briefcaseName = briefcaseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Briefcase briefCase)) return false;
        return id == briefCase.id && Objects.equals(owner, briefCase.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner);
    }

    @Override
    public String toString() {
        return "Briefcase{" +
                "id=" + id +
                ", briefcaseName='" + briefcaseName + '\'' +
                ", depositedBalance=" + depositedBalance +
                ", balanceNow=" + balanceNow +
                ", freeBalance=" + freeBalance +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}
