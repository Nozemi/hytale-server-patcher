package io.nozemi.hytale.serverpatcher.transformers;

import com.hypixel.hytale.plugin.early.ClassTransformer;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.util.Objects;

public class PlayerRemovedSystemTransformer implements ClassTransformer {
    private static final String PACKAGE_NAME = "com.hypixel.hytale.server.core.modules.entity.player";
    private static final String CLASS_NAME = "PlayerSystems$PlayerRemovedSystem";
    private static final String METHOD_NAME = "onEntityRemoved";

    @NullableDecl
    @Override
    public byte[] transform(@NonNullDecl String name, @NonNullDecl String path, @NonNullDecl byte[] bytes) {
        if (!name.equals(String.format("%s.%s", PACKAGE_NAME, CLASS_NAME))) return bytes;

        var reader = new ClassReader(bytes);
        var writer = new ClassWriter(reader, 0);
        var visitor = new PlayerRemovedSystemVisitor(Opcodes.ASM9, writer);
        reader.accept(visitor, 0);

        return writer.toByteArray();
    }

    private static class PlayerRemovedSystemVisitor extends ClassVisitor {

        protected PlayerRemovedSystemVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            var mv = super.visitMethod(access, name, descriptor, signature, exceptions);

            if (!Objects.equals(name, METHOD_NAME))
                return mv;

            return new EntityRemovedMethodVisitor(api, mv);
        }
    }

    private static class EntityRemovedMethodVisitor extends MethodVisitor {

        protected EntityRemovedMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (opcode == Opcodes.INVOKESTATIC && owner.endsWith("PlayerUtil") && Objects.equals(name, "broadcastMessageToPlayers"))
                return;

            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }
}
