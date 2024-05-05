package com.janboerman.invsee.spigot.impl_1_20_5_R4;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.logging.Logger;

import net.minecraft.world.Container;

public class AddItemTest {

    private static final Logger LOGGER = Logger.getLogger(AddItemTest.class.getName());
    private static final int INVENTORY_MAX_STACK = Container.MAX_STACK;

    private ItemStack[] contents;

    @BeforeAll
    public static void setUpClass() {
        Server server = mock(Server.class);
        when(server.getItemFactory()).thenReturn(new FakeItemFactory());
        when(server.getLogger()).thenReturn(LOGGER);
        Bukkit.setServer(server);

    }

    @BeforeEach
    public void setUp() {
        this.contents = new ItemStack[54];
    }

    private void addItem(ItemStack stack) {
        MainBukkitInventory.addItem(contents, stack, INVENTORY_MAX_STACK);
    }

    @Test
    public void testFromEmpty() {
        addItem(itemStack(Material.DIAMOND, 1));

        assertContents(itemStack(Material.DIAMOND, 1));
    }

    @Test
    public void testOverflow() {
        addItem(itemStack(Material.DIAMOND, 128));
        addItem(itemStack(Material.EMERALD, 65));

        assertContents(
                itemStack(Material.DIAMOND, 64), itemStack(Material.DIAMOND, 64),
                itemStack(Material.EMERALD, 64), itemStack(Material.EMERALD, 1));
    }

    @Test
    public void testOverflowWithCustomMaxStackSize() {
        addItem(itemStack(Material.DIAMOND, 128, 99));

        assertContents(itemStack(Material.DIAMOND, 99, 99), itemStack(Material.DIAMOND, 128 - 99, 99));
    }

    @Test
    public void testMerge() {
        contents[0] = itemStack(Material.ENDER_PEARL, 15, 17);

        addItem(itemStack(Material.ENDER_PEARL, 34, 17));

        // expected: 17, 17, 15
        assertContents(
                itemStack(Material.ENDER_PEARL, 17, 17),
                itemStack(Material.ENDER_PEARL, 17, 17),
                itemStack(Material.ENDER_PEARL, 15, 17));
    }

    private void assertContents(ItemStack... expectedPrefix) {
        ItemStack[] actual = contents;
        for (int i = 0; i < expectedPrefix.length; i += 1) {
            assertEquals(expectedPrefix[i], actual[i]);
        }
    }

    private static ItemStack itemStack(Material material, int count) {
        return new ItemStack(material, count);
    }

    private static ItemStack itemStack(Material material, int count, int maxCount) {
        ItemStack stack = new ItemStack(material, count);
        ItemMeta meta = stack.getItemMeta();
        meta.setMaxStackSize(maxCount);
        stack.setItemMeta(meta);
        return stack;
    }

}
