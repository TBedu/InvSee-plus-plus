package com.janboerman.invsee.spigot.addon.give.impl_1_20_5_R4;

import com.janboerman.invsee.spigot.addon.give.common.GiveApi;
import com.janboerman.invsee.spigot.addon.give.common.ItemType;
import com.janboerman.invsee.utils.Either;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import org.bukkit.inventory.ItemStack;


public class GiveImpl implements GiveApi {

    public static final GiveImpl INSTANCE = new GiveImpl();

    private GiveImpl() {
    }

    @Override
    public ItemStack applyTag(ItemStack stack, String tag) {
        if (tag == null) {
            return stack;
        } else {
            throw new IllegalArgumentException("InvSee++ for Minecraft 1.20.5 and up does not support NBT tags on item stacks.");
        }
    }

    @Override
    public Either<String, ItemType> parseItemType(String itemType) {
        try {
            return Either.right(new WithComponents(ItemParser.parseItemType(itemType)));
        } catch (CommandSyntaxException e) {
            return Either.left(e.getMessage());
        }
    }
}
