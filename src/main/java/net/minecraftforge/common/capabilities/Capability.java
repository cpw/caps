/*
 * Minecraft Forge
 * Copyright (c) 2016-2018.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.common.capabilities;

import java.util.concurrent.Callable;
import javax.annotation.Nullable;
//Same class as in Forge now, no modifications. Change a couple types that referenced MC code.

public class Capability<T>
{
    public static interface IStorage<T>
    {
        @Nullable
        NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side);
        void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt);
    }

    public String getName() { return name; }
    public IStorage<T> getStorage() { return storage; }
    public void readNBT(T instance, EnumFacing side, NBTBase nbt) {
        storage.readNBT(this, instance, side, nbt);
    }
    @Nullable
    public NBTBase writeNBT(T instance, EnumFacing side) {
        return storage.writeNBT(this, instance, side);
    }
    @Nullable
    public T getDefaultInstance() {
        try {
            return this.factory.call();
        } catch (Exception e) {
            //Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
    }
    @SuppressWarnings("unchecked")
    public <R> R cast(T instance) { return (R)instance; }

    // INTERNAL
    private final String name;
    private final IStorage<T> storage;
    private final Callable<? extends T> factory;

    Capability(String name, IStorage<T> storage, Callable<? extends T> factory)
    {
        this.name = name;
        this.storage = storage;
        this.factory = factory;
    }
}
