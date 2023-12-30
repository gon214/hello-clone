package org1.example.pluginsample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin_sample extends JavaPlugin implements Listener {

  private int count;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  /**
   * �v���C���[���X�j�[�N���J�n/�I������ۂɋN�������C�x���g�n���h���B
   *
   * @param e �C�x���g
   */
  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) throws IOException {
    // �C�x���g�������̃v���C���[�⃏�[���h�Ȃǂ̏���ϐ��Ɏ��B
    Player player = e.getPlayer();
    World world = player.getWorld();

    List<Color> colorList = List.of(Color.RED, Color.BLUE, Color.WHITE, Color.BLACK);

    if (count % 2 == 0) {
      for (Color color : colorList) {
        // �ԉ΃I�u�W�F�N�g���v���C���[�̃��P�[�V�����n�_�ɑ΂��ďo��������B
        Firework firework = world.spawn(player.getLocation(), Firework.class);

        // �ԉ΃I�u�W�F�N�g�������^�����擾�B
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // ���^���ɑ΂��Đݒ��ǉ�������A�l�̏㏑�����s���B
        // ����͐F�Ő��^�̉ԉ΂�ł��グ��B
        fireworkMeta.addEffect(
            FireworkEffect.builder()
                .withColor(color)
                .with(Type.BALL_LARGE)
                .withFlicker()
                .build());
        fireworkMeta.setPower(1);

        // �ǉ��������ōĐݒ肷��B
        firework.setFireworkMeta(fireworkMeta);
      }
      Path path = Path.of("firework.txt");
      Files.writeString(path, "���[�܂�[");
      player.sendMessage(Files.readString(path));
    }
    count++;
  }

  @EventHandler
  public void onPlayerBedEnter(PlayerBedEnterEvent e) {
    Player player = e.getPlayer();
    ItemStack[] itemStacks = player.getInventory().getContents();
    Arrays.stream(itemStacks)
        .filter(item -> !Objects.isNull(item) && item.getMaxStackSize() == 64 && item.getAmount() < 64)
        .forEach(item -> item.setAmount(64));

    player.getInventory().setContents(itemStacks);
  }
}


