package domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author Albert
 */
@Table(name = "customers")
public class Customer {
    @PrimaryKey
    @Column(name = "id")
    public String id;

    @NotNull
    @NotBlank
    @Column(name = "name")
    public String name;
}
