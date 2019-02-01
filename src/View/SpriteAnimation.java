package View;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
    private ImageView imageView;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    private int lastIndex;

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
            int count, int columns,
            int offsetX, int offsetY,
            int width, int height) {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    public SpriteAnimation(ImageView imageView, int count, int columns) {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = 0;
        this.offsetY = 0;
        this.width = (int) imageView.getImage().getWidth() / columns;
        this.height = (int) imageView.getImage().getHeight() / (count / columns);
        setCycleDuration(new Duration(1000));
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double frac) {

    }

    protected void interpolate(int k) {
        int index = lastIndex + k;
        if(count > 4) {
            if (index == count - 1) index = 0;
            if (index == -1) index = count - 7;
        }
        else {
            if(index == count)
                index = 0;
        }
        if (index != lastIndex) {
            final int x = (index % columns) * width + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
