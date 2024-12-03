package antidimon.web.restservice.models.entities.operation;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "operation_statuses")
@NoArgsConstructor
@Getter
@Setter
public class OperationStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OperationStatuses operationStatus;

    @OneToMany(mappedBy = "operationStatus", fetch = FetchType.LAZY)
    private List<Operation> operationList;

    public OperationStatusEntity(OperationStatuses operationStatus) {
        this.operationStatus = operationStatus;
    }

    @Override
    public String toString() {
        return "OperationStatusEntity{" +
                "id=" + id +
                ", operationStatus=" + operationStatus +
                '}';
    }
}
