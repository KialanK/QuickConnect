package de.kialank.quickconnect.mixin;

import de.kialank.quickconnect.QuickConnect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "addNormalWidgets", at = @At("TAIL"))
    private void addCustomButtons(int y, int spacingY, CallbackInfoReturnable<Integer> ci) {
        if (QuickConnect.getFirstName() != null) {
            this.addDrawableChild(ButtonWidget.builder(Text.translatable(String.valueOf(QuickConnect.getFirstName().charAt(0))), (button ->
                            connectToServer(QuickConnect.getFirstAddress(), client.currentScreen)))
                    .dimensions(this.width / 2 + 104, y - 2 * spacingY, 20, 20)
                    .tooltip(Tooltip.of(Text.literal("Join " + QuickConnect.getFirstName())))
                    .build());
        }

        if (QuickConnect.getSecondName() != null) {
            this.addDrawableChild(ButtonWidget.builder(Text.translatable(String.valueOf(QuickConnect.getSecondName().charAt(0))), (button ->
                            connectToServer(QuickConnect.getSecondAddress(), client.currentScreen)))
                    .dimensions(this.width / 2 + 104, y - spacingY, 20, 20)
                    .tooltip(Tooltip.of(Text.literal("Join " + QuickConnect.getSecondName())))
                    .build());
        }

        if (QuickConnect.getThirdName() != null) {
            this.addDrawableChild(ButtonWidget.builder(Text.translatable(String.valueOf(QuickConnect.getThirdName().charAt(0))), (button ->
                            connectToServer(QuickConnect.getThirdAddress(), client.currentScreen)))
                    .dimensions(this.width / 2 + 104, y, 20, 20)
                    .tooltip(Tooltip.of(Text.literal("Join " + QuickConnect.getThirdName())))
                    .build());
        }
    }

    @Unique
    private void connectToServer(String ip, Screen parentScreen) {
        MinecraftClient client = MinecraftClient.getInstance();
        ServerInfo serverInfo = new ServerInfo("Mein Server", ip + ":" + 25565, ServerInfo.ServerType.OTHER);
        ServerAddress serverAddress = ServerAddress.parse(ip + ":" + 25565);
        ConnectScreen.connect(parentScreen, client, serverAddress, serverInfo, false, null);
    }
}