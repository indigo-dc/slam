package pl.cyfronet.bazaar.engine.extension.bean;

import com.agreemount.bean.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by km on 03.08.16.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IndigoDocument extends Document{
    private String site;
    private String siteName;
}
