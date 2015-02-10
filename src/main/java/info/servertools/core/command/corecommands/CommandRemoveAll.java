/*
 * This file is a part of ServerTools <http://servertools.info>
 *
 * Copyright (c) 2014 ServerTools
 * Copyright (c) 2014 contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.servertools.core.command.corecommands;

import info.servertools.core.ServerTools;
import info.servertools.core.command.CommandLevel;
import info.servertools.core.command.ServerToolsCommand;
import info.servertools.core.task.RemoveAllTickTask;

import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.registry.GameData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

public class CommandRemoveAll extends ServerToolsCommand {

    public CommandRemoveAll(String defaultName) {
        super(defaultName);
    }

    @Override
    public CommandLevel getCommandLevel() {

        return CommandLevel.OP;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {

        return "/" + name + " [blockName] {radius}";
    }

    @Nullable
    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> blockNames = getBlockNames();
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, blockNames.toArray(new String[blockNames.size()])) : null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] strings) throws CommandException {

        if (!(sender instanceof EntityPlayerMP))
            throw new WrongUsageException("Only players can use that command");

        if (strings.length < 1)
            throw new WrongUsageException(getCommandUsage(sender));

        EntityPlayerMP player = (EntityPlayerMP) sender;
        int range = 15;

        if (strings.length >= 2)
            range = Integer.parseInt(strings[1]);

        Set<Block> blocksToClear = new HashSet<>();

        for (Object obj : GameData.getBlockRegistry()) {
            if (obj instanceof Block) {

                String blockName = ((Block) obj).getUnlocalizedName();
                if (blockName.startsWith("tile."))
                    blockName = blockName.substring(5, blockName.length());

                if (blockName.equalsIgnoreCase(strings[0]) && !(obj == Blocks.air)) {
                    blocksToClear.add((Block) obj);
                }
            }
        }

        if (blocksToClear.isEmpty())
            throw new CommandException("That block can't be found. Try using tab-completion");

        ServerTools.instance.tickHandler.registerTask(new RemoveAllTickTask(player, range, blocksToClear));
    }

    private static List<String> getBlockNames() {

        List<String> list = Lists.newArrayList();

        for (Object obj : GameData.getBlockRegistry()) {
            if (obj instanceof Block) {
                String blockName = ((Block) obj).getUnlocalizedName();
                if (blockName.startsWith("tile."))
                    blockName = blockName.substring(5, blockName.length());

                list.add(blockName);
            }
        }

        return list;
    }
}
