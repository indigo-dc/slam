package pl.cyfronet.ltos.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bwilk
 *
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {
    	
    @Id
    @Column(unique=true)
    private String login;
	private String password;
	
	private boolean admin;	
	
    @OneToOne
    private User user;
   
}
