package info.servertools.core.config;

import static info.servertools.core.util.STConfig.CATEGORY_CHAT;
import static info.servertools.core.util.STConfig.CATEGORY_GENERAL;
import static info.servertools.core.util.STConfig.CATEGORY_WORLD;

import info.servertools.core.STVersion;
import info.servertools.core.lib.Environment;
import info.servertools.core.util.STConfig;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public final class CoreConfig {

    public static final STConfig stConfig;

    public static boolean ENABLE_HELP_OVERRIDE;
    public static boolean ENABLE_FLAT_BEDROCK;
    public static boolean ENABLE_BLOCK_BREAK_LOG;
    public static boolean ENABLE_BLOCK_PLACE_LOG;
    public static boolean ENABLE_MOTD_ON_LOGIN;
    public static boolean ENABLE_OP_PREFIX;
    public static String OP_PREFIX;
    public static String VOICE_PREFIX;
    public static List<String> SILENCE_BLACKLISTED_COMMANDS;

    static {
        stConfig = new STConfig(new File(Environment.SERVERTOOLS_DIR, "core.cfg"), STVersion.VERSION);
        load();
    }

    public static void load() {

        final Configuration config = stConfig.getConfig();

        String category;
        Property prop;

        category = CATEGORY_GENERAL;
        {
            prop = config.get(category, "enable-help-override", true);
            prop.comment = "Overrides the /help command to make it work with broken mod commands";
            prop.setRequiresWorldRestart(true);
            ENABLE_HELP_OVERRIDE = prop.getBoolean();
        }

        category = CATEGORY_WORLD;
        {
            prop = config.get(category, "enable-flat-bedrock", true);
            prop.comment = "Makes newly generated chunks have a flat one layer thick level of bedrock";
            prop.setRequiresMcRestart(true);
            ENABLE_FLAT_BEDROCK = prop.getBoolean();
        }

        category = CATEGORY_WORLD + ".Logging";
        {
            prop = config.get(category, "enable-block-break-log", false);
            prop.comment = "Enable logging whenever a player breaks a block on the server";
            prop.setRequiresMcRestart(true);
            ENABLE_BLOCK_BREAK_LOG = prop.getBoolean();

            prop = config.get(category, "enable-block-place-log", false);
            prop.comment = "Enable logging whenever a player places a block on the server";
            prop.setRequiresMcRestart(true);
            ENABLE_BLOCK_PLACE_LOG = prop.getBoolean();
        }

        category = CATEGORY_CHAT;
        {
            prop = config.get(category, "enable-motd-on-login", true);
            prop.comment = "Enables sending the MOTD to players when the log onto the server";

            prop = config.get(category, "voice-prefix", "+");
            prop.comment = "The prefix to use for voiced players in chat";
            VOICE_PREFIX = prop.getString();

            prop = config.get(category, "silence-blacklisted-commands", new String[]{"tell", "tellraw", "me", "say"});
            prop.comment = "The commands that silenced players aren't allowed to use";
            SILENCE_BLACKLISTED_COMMANDS = Arrays.asList(prop.getStringList());
        }

        category = CATEGORY_CHAT + ".OPPrefix";
        {
            prop = config.get(category, "enable-op-prefix", true);
            prop.comment = "Enables a prefix on OP messages in chat";
            ENABLE_OP_PREFIX = prop.getBoolean();

            prop = config.get(category, "op-prefix", "OP");
            prop.comment = "The prefix to use for OPs in chat";
            OP_PREFIX = prop.getString();
        }

        stConfig.saveIfChanged();
    }

    private CoreConfig() {}
}
