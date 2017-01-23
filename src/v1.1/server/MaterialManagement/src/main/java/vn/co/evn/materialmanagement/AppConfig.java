/*
 * Copyright©2017 NTT corp． All Rights Reserved．
 */
package vn.co.evn.materialmanagement;

import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppConfig {

    /** The hib properties. */
    private Properties hibProperties;

    /** The root directory. */
    private String rootDirectory;
}
