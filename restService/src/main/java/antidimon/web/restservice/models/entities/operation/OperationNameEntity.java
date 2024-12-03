package antidimon.web.restservice.models.entities.operation;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "operation_names")
@NoArgsConstructor
@Getter
@Setter
public class OperationNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private OperationNames operationName;

    @OneToMany(mappedBy = "operationName", fetch = FetchType.LAZY)
    private List<Operation> operations;

    public OperationNameEntity(OperationNames operationName) {
        this.operationName = operationName;
    }

    @Override
    public String toString() {
        return "OperationNameEntity{" +
                "id=" + id +
                ", operationName=" + operationName + '}';
    }
}
