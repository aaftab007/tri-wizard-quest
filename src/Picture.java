import javax.swing.ImageIcon;
import java.awt.Image;

public class Picture {
    
    private final ImageIcon picture;

    public Picture(String imageName, int width, int height) {
        this.picture = createScaledImageIcon(imageName, width, height);
    }

    public ImageIcon getPicture() {
        return picture;
    }

    private ImageIcon createScaledImageIcon(String imageName, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/pictures/" + imageName + ".gif"));
            return new ImageIcon(originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_FAST));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
