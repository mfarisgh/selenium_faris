package com.emeff23.stc.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PropFileMgmt {

    private static final Logger LogThis = LogManager.getLogger(PropFileMgmt.class.getName());

    public static String getPropertyValue(String propName) {

        String propValue = "";
        Properties properties = new Properties();
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(CentralVars.ProjectPath + "/src/test/resources/local.properties"));
            properties.load(inputStream);

            //LogThis.debug("propName = " + propName);

            propValue = properties.getProperty(propName);

            //LogThis.debug("propValue = " + propValue);

            propValue = resolveEnvVars(propValue);

            //LogThis.debug("new propValue = " + propValue);

        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());

        }

        return propValue;

    }

    /*
     * Returns input string with environment variable references expanded, e.g. $SOME_VAR or ${SOME_VAR}
     */
    public static String resolveEnvVars(String input)
    {
        if (null == input)
        {
            return null;
        }
        // match ${ENV_VAR_NAME} or $ENV_VAR_NAME
        Pattern p = Pattern.compile("\\$\\{(\\w+)\\}|\\$(\\w+)");
        Matcher m = p.matcher(input); // get a matcher object
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            String envVarName = null == m.group(1) ? m.group(2) : m.group(1);

            //LogThis.debug("envVarName = " + envVarName);

            String envVarValue = System.getenv(envVarName);

            //LogThis.debug("envVarValue = " + envVarValue);

            m.appendReplacement(sb, null == envVarValue ? "" : envVarValue);
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
