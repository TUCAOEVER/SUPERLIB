package com.tucaoever.superlib.util;

import org.bukkit.util.Vector;
import org.bukkit.util.BlockVector;
import java.util.NoSuchElementException;
import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class CuboidRegion implements Iterable<Block>
{
    private final Location point1;
    private final Location point2;
    private final int maxX;
    private final int maxY;
    private final int maxZ;
    private int nextX;
    private int nextY;
    private int nextZ;
    
    public CuboidRegion(final Location point1, final Location point2) {
        this.point1 = point1;
        this.point2 = point2;
        this.maxX = this.max().getBlockX();
        this.maxY = this.max().getBlockY();
        this.maxZ = this.max().getBlockZ();
        this.nextX = this.min().getBlockX();
        this.nextY = this.min().getBlockY();
        this.nextZ = this.min().getBlockZ();
    }
    
    @Override
    public Iterator<Block> iterator() {
        return new Iterator<Block>() {
            @Override
            public boolean hasNext() {
                return CuboidRegion.this.nextX != Integer.MIN_VALUE;
            }
            
            @Override
            public Block next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final Block result = new Location(CuboidRegion.this.point1.getWorld(), (double)CuboidRegion.this.nextX, (double)CuboidRegion.this.nextY, (double)CuboidRegion.this.nextZ).getBlock();
                this.forwardOne();
                this.forward();
                return result;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
            public void forwardOne() {
                final CuboidRegion this$0 = CuboidRegion.this;
                final int n = this$0.nextX + 1;
                CuboidRegion.access$4(this$0, n);
                if (n <= CuboidRegion.this.maxX) {
                    return;
                }
                CuboidRegion.access$4(CuboidRegion.this, CuboidRegion.this.min().getBlockX());
                final CuboidRegion this$2 = CuboidRegion.this;
                final int n2 = this$2.nextY + 1;
                CuboidRegion.access$6(this$2, n2);
                if (n2 <= CuboidRegion.this.maxY) {
                    return;
                }
                CuboidRegion.access$6(CuboidRegion.this, CuboidRegion.this.min().getBlockY());
                final CuboidRegion this$3 = CuboidRegion.this;
                final int n3 = this$3.nextZ + 1;
                CuboidRegion.access$8(this$3, n3);
                if (n3 <= CuboidRegion.this.maxZ) {
                    return;
                }
                CuboidRegion.access$4(CuboidRegion.this, Integer.MIN_VALUE);
            }
            
            public void forward() {
                while (this.hasNext() && !CuboidRegion.this.checkHas((Vector)new BlockVector(CuboidRegion.this.nextX, CuboidRegion.this.nextY, CuboidRegion.this.nextZ))) {
                    this.forwardOne();
                }
            }
        };
    }
    
    public Vector min() {
        return new Vector(Math.min(this.point1.getBlockX(), this.point2.getBlockX()), Math.min(this.point1.getBlockY(), this.point2.getBlockY()), Math.min(this.point1.getBlockZ(), this.point2.getBlockZ()));
    }
    
    public Vector max() {
        return new Vector(Math.max(this.point1.getBlockX(), this.point2.getBlockX()), Math.max(this.point1.getBlockY(), this.point2.getBlockY()), Math.max(this.point1.getBlockZ(), this.point2.getBlockZ()));
    }
    
    public boolean checkHas(final Vector pt) {
        final double x = pt.getX();
        final double y = pt.getY();
        final double z = pt.getZ();
        final Vector min = this.min();
        final Vector max = this.max();
        return x >= min.getBlockX() && x <= max.getBlockX() && y >= min.getBlockY() && y <= max.getBlockY() && z >= min.getBlockZ() && z <= max.getBlockZ();
    }
    
    static /* synthetic */ void access$4(final CuboidRegion cuboidRegion, final int nextX) {
        cuboidRegion.nextX = nextX;
    }
    
    static /* synthetic */ void access$6(final CuboidRegion cuboidRegion, final int nextY) {
        cuboidRegion.nextY = nextY;
    }
    
    static /* synthetic */ void access$8(final CuboidRegion cuboidRegion, final int nextZ) {
        cuboidRegion.nextZ = nextZ;
    }
}
