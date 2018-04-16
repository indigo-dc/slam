package pl.cyfronet.indigo.bean.legacy;

/**
 * Created by chomik on 27.01.16.
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
@lombok.EqualsAndHashCode
@lombok.NoArgsConstructor
public class CreateGrantData {
    private String grantId;
    private String team;
    private Integer affiliationId;
    private String branchOfScienceId;
}
