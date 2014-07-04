package net.chrisliu.minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import net.chrisliu.minecraft.command.CommandHome;
import net.chrisliu.minecraft.network.PacketPipeline;
import net.chrisliu.minecraft.proxy.CommonProxy;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ChrisLiuMod.MODID, name = ChrisLiuMod.MODID, version = ChrisLiuMod.VERSION)
public final class ChrisLiuMod
{
	public static final String MODID = "ChrisliuMod";
    public static final String VERSION = "1.1";
	public static final String CONFIG_URL = "https://dl.dropboxusercontent.com/u/209570/Minecraft/ChrisLiuMod_config.txt";
	public static double d0, d1, d2;
	public static Logger gbLog = Logger.getLogger(MODID);
	
	@Instance("ChrisLiuMod")
	public static ChrisLiuMod instance;

	@SidedProxy(clientSide = "net.chrisliu.minecraft.proxy.ClientProxy", serverSide = "net.chrisliu.minecraft.proxy.CommonProxy")
	public static CommonProxy proxy;

	/** Updated PacketHandler from Forge wiki tutorial: http://www.minecraftforge.net/wiki/Netty_Packet_Handling */
	public static final PacketPipeline packetPipeline = new PacketPipeline();

	/** This is used to keep track of GUIs that we make*/
	private static int modGuiIndex = 0;

	/** Custom GUI indices: */
	public static final int
	GUI_TELEPORT = modGuiIndex++;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Configuration config = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + "/Tutorial.cfg"));
		//config.load();
		//wizardArmorFlag = config.get(Configuration.CATEGORY_GENERAL, "WizardArmorFlag", true).getBoolean(true);
		//config.save();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		packetPipeline.initialise();
		proxy.registerRenderers();
		//MinecraftForge.EVENT_BUS.register(new TutEventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
		getHomeLocation();
	}

	@EventHandler
	public void postInitialise(FMLPostInitializationEvent event) {
		packetPipeline.postInitialise();
		
		// this is generally a good place to modify recipes or otherwise interact with other mods
	}
	
	@EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
      event.registerServerCommand(new CommandHome());
      //event.registerServerCommand(new CommandTP());
    }
	
	private void getHomeLocation()
    {
        gbLog.info("getHomeLocation()");
        String content = "", line;

        try
        {
            gbLog.info("Fetch config file from " + ChrisLiuMod.CONFIG_URL);
            URL url = new URL(ChrisLiuMod.CONFIG_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = rd.readLine()) != null)
            {
                content += line + "\n";
            }

            gbLog.info("Config File Content: ");
            gbLog.info(content);
            String[] locs = content.split(",");
            d0 = Double.parseDouble(locs[0]);
            d1 = Double.parseDouble(locs[1]);
            d2 = Double.parseDouble(locs[2]);
            gbLog.info("home_locs = " + d0 + ", " + d1 + ", " + d2);
            
        }
        catch (Exception e)
        {
            gbLog.info("getHomeLocation() error: " + e.getMessage());
        }

        //return content;
    }
}
