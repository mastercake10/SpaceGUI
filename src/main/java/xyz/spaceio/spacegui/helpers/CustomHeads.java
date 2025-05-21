package xyz.spaceio.spacegui.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

public class CustomHeads {
    public enum CustomHead {
        PLUS("55dea4fb-3202-4c60-987d-c7db2f726a38", "http://textures.minecraft.net/texture/7438d08bd0405c05f47ea86d66643434fdd2e8c46ff1e6f882bb9bf891c7d3a5", "STONE_BUTTON"),
        SLASH("e8d66c31-f0cb-4b75-9d6f-b590e2fb1dc6", "http://textures.minecraft.net/texture/667a926f2b0322bf80da7dc65960c579f96ba72feaf258fb036c803f1bdba25a", "STONE_BUTTON"),
        BACKSLASH("904b16c2-c05b-48cd-a55f-4c08a868cbd0", "http://textures.minecraft.net/texture/6b99b0c00638911abc7b1934e05144479078b9c7f010136fc6681b9b25a5f7f", "STONE_BUTTON"),
        SQUARE_BRACKET_OPEN("4fad8744-4428-4fc2-8f7f-2efa160bf647", "http://textures.minecraft.net/texture/592e0c80abe4f892b7f04a8e6f882bb9bf891c7d3a5", "STONE_BUTTON"),
        SQUARE_BRACKET_CLOSED("93837258-eb62-448c-aa9c-9c6c82e84e8b", "http://textures.minecraft.net/texture/40189f108bb59fa599dd91d83a6d696c3434fdd2e8c46ff1e6f882bb9bf891c7d3a5", "STONE_BUTTON"),
        ROUND_BRACKET_OPEN("addfa97f-74e8-4d39-a52d-020cc4354ce7", "http://textures.minecraft.net/texture/118ebed3c0de5ba0969a6db650cb9bb119fbf32acf535b892438d97a6ae8f6f", "STONE_BUTTON"),
        ROUND_BRACKET_CLOSED("9a6dedb6-2926-4a2d-a442-6c00395dfe6e", "http://textures.minecraft.net/texture/95899a3cf4ef0f88490f56af66fdfe6c408d95dfe6f3bb41d90", "STONE_BUTTON"),
        CURLY_BRACKET_OPEN("f73fb622-5043-4759-a75c-1e24d95b8da2", "http://textures.minecraft.net/texture/5e1e1dd9800f2fc594f0abb17b2fd4965c191c235e35f3f9a5a6681b92a5f7f", "STONE_BUTTON"),
        CURLY_BRACKET_CLOSED("96bc3f71-7b37-4932-b10c-94e3f4b41d90", "http://textures.minecraft.net/texture/9b812c80a22f043889a16c49df598834159e10e0d09f34fe5de3828215169520fd", "STONE_BUTTON"),
        MINUS("f837c250-1b65-49b6-9ca5-d9923fc25e5c", "http://textures.minecraft.net/texture/473fd8a06e6ea829e8fc2ed1b18d4474907fb0af3ee648bdaabe6683011755353595359", "STONE_BUTTON"),
        EQUALS("cf079a98-dfb7-4c23-b595-54b8b767dac3", "http://textures.minecraft.net/texture/80e71b5ceeb98df098fab7fa99738de9472dedd444bd41a0bf2dc77e5ffb3fe", "STONE_BUTTON"),
        OCTOTHORPE("f8c52b16-f1a0-486f-a36c-1c82e2d9587f", "http://textures.minecraft.net/texture/e22fb2ded7b5033a2ecd49a9163e7431e4060884755353595359", "STONE_BUTTON"),
        PERCENT_SIGN("7b327849-ecd5-41de-9604-35994095f6e8", "http://textures.minecraft.net/texture/8458ca966da829e8fc2ed1b18d4474907fb0af3ee648bdaabe6683011755353595359", "STONE_BUTTON"),
        UNDERSCORE("b03bcc1d-355b-4a83-bb11-24598eb5b7d9", "http://textures.minecraft.net/texture/105a152d446591ba098fc5c12817e8f59a974ed85bd47a996dd05b60d16fffb0c51f9af9c3087a5057504", "STONE_BUTTON"),

        ARROW_UP("2093c209-6db8-4c3b-9d46-d94b1b5fb8a5", "http://textures.minecraft.net/texture/45c588b9ec0a08a37e01a809ed0903cc34c3e3f176dc92230417da93b948f148", "ARROW"),
        ARROW_DOWN("fa8d38d7-5e5e-4a58-a3c4-ca7e9d0dcb91", "http://textures.minecraft.net/texture/1cb8be16d40c25ace64e09f6086d408ebc3d545cfb2990c5b6c25dabcedea07a", "ARROW"),
        ARROW_LEFT("87df6a68-e975-4751-9265-f1ec9402370e", "http://textures.minecraft.net/texture/76ebaa41d1d405eb6b60845bb9ac724af70e85eac8a96a5544b9e23ad6c96c62", "ARROW"),
        ARROW_RIGHT("588c30b9-472b-47b5-ad40-8549f67fadeb", "http://textures.minecraft.net/texture/8399e5da82ef7765fd5e472f3147ed118d981887730ea7bb80d7a1bed98d5ba", "ARROW"),
        ARROW_LEFT_UP("31664d24-2e8e-4ee9-bdee-fcc10ae16b55", "http://textures.minecraft.net/texture/1b701c1f05e319d6b28f61b28b6a7e2a846a510de322bdc96e94a2388b78469", "ARROW"),
        ARROW_LEFT_DOWN("f3a711a7-01b0-4521-abb8-2d309542bf41", "http://textures.minecraft.net/texture/4be020e7408d8aaded70c993f59b525504c95fa61a39db484e99fd31e99a6", "ARROW"),
        ARROW_RIGHT_UP("999608a8-8149-46b6-a2cc-bff6174168ad", "http://textures.minecraft.net/texture/212ebed49b176284d063f790af2b0081f22749e915868e26a0373bae96dc804f", "ARROW"),
        ARROW_RIGHT_DOWN("df5d1858-284c-4c5e-82d4-1f9ae2786d16", "http://textures.minecraft.net/texture/e57f2e12b66096149c31d28b1c835731347715cf8e86b68dea07bea0459d837", "ARROW");
        private final UUID uuid;
        private final URL url;
        private final String fallback;

        CustomHead(String uuid, String url, String fallback) {
            this.uuid = UUID.fromString(uuid);
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            this.fallback = fallback;
        }

        public ItemStack get(String displayname) {
            ItemStack itemStack = this.get();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(displayname);
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }

        public ItemStack get() {
            if (Material.getMaterial("PLAYER_HEAD") == null) {
                return new ItemStack(Objects.requireNonNull(Material.getMaterial(this.fallback)));
            }

            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

            try {
                PlayerProfile profile = Bukkit.createPlayerProfile(this.uuid);
                PlayerTextures textures = profile.getTextures();
                textures.setSkin(this.url);
                profile.setTextures(textures);
                skullMeta.setOwnerProfile(profile);
                itemStack.setItemMeta(skullMeta);
            } catch (Exception | NoSuchMethodError e) {
                return new ItemStack(Objects.requireNonNull(Material.getMaterial(fallback)));
            }

            return itemStack;
        }
    }
}
