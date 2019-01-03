package assignment;
/**
 *
 * CS314H Programming Assignment 1 - Java image processing
 *
 * Included is the Invert effect from the assignment.  Use this as an
 * example when writing the rest of your transformations.  For
 * convenience, you should place all of your transformations in this file.
 *
 * You can compile everything that is needed with
 * javac -d bin src/assignment/*.java
 *
 * You can run the program with
 * java -cp bin assignment.JIP
 *
 * Please note that the above commands assume that you are in the prog1
 * directory.
 */

import java.lang.reflect.Array;
import java.util.ArrayList;

/*
Remove all red shades from the image.
 */
class NoRed extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Assign thisPixel the same green and blue intensities, but set red to zero.
                pixels[row][col] = makePixel(0, getGreen(thisPixel), getBlue(thisPixel));
            }
        }
        return pixels;
    }
}

/*
Remove all green shades from the image.
 */
class NoGreen extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Assign thisPixel the same red and blue intensities, but set green to zero.
                pixels[row][col] = makePixel(getRed(thisPixel), 0, getBlue(thisPixel));
            }
        }
        return pixels;
    }
}

/*
Remove all blue shades from the image.
 */
class NoBlue extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Assign thisPixel the same red and green intensities, but set blue to zero.
                pixels[row][col] = makePixel(getRed(thisPixel), getGreen(thisPixel), 0);
            }
        }
        return pixels;
    }
}

/*
Preserve any red shades in the image, but remove all green and blue.
 */
class RedOnly extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Assign thisPixel the same red intensity, but set green and blue to zero.
                pixels[row][col] = makePixel(getRed(thisPixel), 0, 0);
            }
        }
        return pixels;
    }
}


/*
Preserve any green shades in the image, but remove all red and blue.
 */
class GreenOnly extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Assign thisPixel the same green intensity, but set red and blue to zero.
                pixels[row][col] = makePixel(0, getGreen(thisPixel), 0);
            }
        }
        return pixels;
    }
}

/*
Preserve any blue shades in the image, but remove all red and green.
 */
class BlueOnly extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Assign thisPixel the same blue intensity, but set red and green to zero.
                pixels[row][col] = makePixel(0, 0, getBlue(thisPixel));
            }
        }
        return pixels;
    }
}


/*
Turn a color image into a black and white image.
 */
class BlackAndWhite extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Compute the average intensity of the three colors.
                int averageIntensity = (getRed(thisPixel) + getGreen(thisPixel) + getBlue(thisPixel)) / 3;

                //Set red, green, and blue to the average intensity of the pixel.
                pixels[row][col] = makePixel(averageIntensity, averageIntensity, averageIntensity);
            }
        }
        return pixels;
    }
}


/*
Reflect the image around a vertical line across the middle of the original image.
 */
class VerticalReflect extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){

            //Iterate the columns of the image only halfway.
            for(int col = 0; col < pixels[row].length / 2; col++){

                //Swap the pixel with the corresponding pixel on the same row.
                swapPixelsHorizontally(pixels, row, col);
            }
        }
        return pixels;
    }

    //Parameters: Image as a 2D array, row and column of the pixel to be swapped
    //Swap the specified pixel with the pixel on the same row but opposite column of the original pixel.
    private void swapPixelsHorizontally(int[][] pixels, int row, int columnOfFirstPixel){
        int columnOfSecondPixel = pixels[row].length - 1 - columnOfFirstPixel;
        int firstPixel = pixels[row][columnOfFirstPixel];
        pixels[row][columnOfFirstPixel] = pixels[row][columnOfSecondPixel];
        pixels[row][columnOfSecondPixel] = firstPixel;
    }
}

/*
Reflect the image around a horizontal line across the middle of the original image.
 */
class HorizontalReflect extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int col = 0; col < pixels[0].length; col++){

            //Iterate the rows of the image only halfway.
            for(int row = 0; row < pixels.length / 2; row++){

                //Swap the pixel with the corresponding pixel on the same column.
                swapPixelsVertically(pixels, col, row);
            }
        }
        return pixels;
    }

    //Parameters: Image as a 2D array, row and column of the pixel to be swapped
    //Swap the specified pixel with the pixel on the same column but opposite row of the original pixel.
    private void swapPixelsVertically(int[][] pixels, int column, int rowOfFirstPixel){
        int rowOfSecondPixel = pixels.length - 1 - rowOfFirstPixel;
        int firstPixel = pixels[rowOfFirstPixel][column];
        pixels[rowOfFirstPixel][column] = pixels[rowOfSecondPixel][column];
        pixels[rowOfSecondPixel][column] = firstPixel;
    }
}

/*
Create an image that is twice as high and twice as wide as your original picture.
 */
class Grow extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){

        //Output array is twice as wide and twice as long the original.
        int[][] output = new int[pixels.length * 2][pixels[0].length * 2];

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Assign thisPixel to the four corresponding pixels in output.
                output[row * 2 + 1][col * 2 + 1] = thisPixel;
                output[row * 2][col * 2 + 1] = thisPixel;
                output[row * 2 + 1][col * 2] = thisPixel;
                output[row * 2][ col * 2] = thisPixel;
            }
        }
        return output;
    }
}


/*
Create an image that is half as high and half as wide as your original picture.
 */
class Shrink extends ImageEffect{
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){

        //Output array is half as wide and half as long the original.
        int[][] output = new int[pixels.length / 2][pixels[0].length / 2];

        //Use nested for loops to iterate on the output array instead of the original.
        for(int row = 0; row < output.length; row++){
            for(int col = 0; col < output[row].length; col++){

                //Compute the average color intensities for the four pixels in the input
                int averageRed = (getRed(pixels[row * 2 + 1][col * 2 + 1]) +
                        getRed(pixels[row * 2][col * 2 + 1]) +
                        getRed(pixels[row * 2 + 1][col * 2]) +
                        getRed(pixels[row * 2][ col * 2])) / 4;
                int averageGreen = (getGreen(pixels[row * 2 + 1][col * 2 + 1]) +
                        getGreen(pixels[row * 2][col * 2 + 1]) +
                        getGreen(pixels[row * 2 + 1][col * 2]) +
                        getGreen(pixels[row * 2][ col * 2])) / 4;
                int averageBlue = (getBlue(pixels[row * 2 + 1][col * 2 + 1]) +
                        getBlue(pixels[row * 2][col * 2 + 1]) +
                        getBlue(pixels[row * 2 + 1][col * 2]) +
                        getBlue(pixels[row * 2][ col * 2])) / 4;

                //Assign the average color intensities onto the corresponding pixel in output.
                output[row][col] = makePixel(averageRed, averageGreen, averageBlue);
            }
        }
        return output;
    }
}


/*
Reduce the number of colors used in the image from millions to eight.
 */
class Threshold extends ImageEffect{
    public Threshold() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Threshold",
                "Set a minimum intensity threshold for each color to appear in the image.\n" +
                "Minimum Threshold: 0\n" +
                "Default Threshold: 127\n" +
                "Maximum Threshold: 255",
                127, 0, 255));

    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){

        //threshold is minimum intensity threshold for each color to appear in the output.
        final int threshold = params.get(0).getValue();

        int[][] output = new int[pixels.length][pixels[0].length];
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //isRed, isGreen, and isBlue are integer variables that determine whether each color should appear in the output.
                int isRed = 0, isGreen = 0, isBlue = 0;
                if(getRed(thisPixel) >= threshold){
                    isRed = 1;
                }
                if(getGreen(thisPixel) >= threshold){
                    isGreen = 1;
                }
                if(getBlue(thisPixel) >= threshold){
                    isBlue = 1;
                }

                //if isRed, isGreen, or isBlue is 1, the corresponding color intensity is 255.
                //else if isRed, isGreen, or isBlue is 0, the corresponding color intensity is 0.
                output[row][col] = makePixel(isRed * 255, isGreen * 255, isBlue * 255);
            }
        }
        return output;
    }
}

/*
Replace each pixel with the average pixel value of a small neighborhood.
 */
class Smooth extends ImageEffect{
    public Smooth() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be smoothened.\n" +
                "Minimum Neighborhood Radius: 1\n" +
                "Default Neighborhood Radius: 2\n" +
                "Maximum Neighborhood Radius: 3",
                2, 1, 3));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){

        //neighborhoodRadius is the radius for the neighborhood to be smoothened.
        final int neighborhoodRadius = params.get(0).getValue();

        int[][] output = new int[pixels.length][pixels[0].length];
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //averageRed, averageGreen, and averageBlue represent the average color intensity in the neighborhood.
                int averageRed = 0, averageGreen = 0, averageBlue = 0;
                int count = 0;

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {


                            int thisPixel = pixels[row + i][col + j];
                            averageRed += getRed(thisPixel);
                            averageGreen += getGreen(thisPixel);
                            averageBlue += getBlue(thisPixel);
                            count++;
                        }
                    }
                }
                averageRed /= count;
                averageGreen /= count;
                averageBlue /= count;
                output[row][col] = makePixel(averageRed, averageGreen, averageBlue);
            }
        }
        return output;
    }
}

/*
Light pixels near dark pixels should be made lighter, and dark pixels near light pixels should be made darker.
 */
class Sharpen extends ImageEffect{
    public Sharpen() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be sharpened.\n" +
                        "Minimum Neighborhood Radius: 1\n" +
                        "Default Neighborhood Radius: 1\n" +
                        "Maximum Neighborhood Radius: 3",
                1, 1, 3));
        params.add(new ImageEffectParam("Sharpness Intensity",
                "Set how much darker dark pixels should get,\n" +
                        "and how much lighter light pixels should get.\n" +
                        "Minimum Sharpness Intensity: 1\n" +
                        "Default Sharpness Intensity: 100\n" +
                        "Maximum Sharpness Intensity: 500",
                100, 10, 500));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int[][] output = new int[pixels.length][pixels[0].length];

        //neighborhoodRadius is the radius for the neighborhood to be sharpened.
        final int neighborhoodRadius = params.get(0).getValue();

        //sharpnessIntensity represents how much darker dark pixels should get,
        //and how much lighter light pixels should get.
        final int sharpnessIntensity = params.get(1).getValue();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //averageIntensity is the average intensity of every pixel within the neighborhood
                int averageIntensity = 0;

                int count = 0;

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {

                            averageIntensity += averageIntensity(pixels[row + i][col + j]);
                            count++;
                        }
                    }
                }
                averageIntensity /= count;

                //Lighten the original pixel according to the difference between the
                //average intensity of the neighborhood and of the pixel, and according to
                //a factor of the sharpnessIntensity. This new pixel is assigned to the
                //corresponding pixel in output.
                output[row][col] = lighten(pixels[row][col],
                        (averageIntensity(pixels[row][col]) - averageIntensity) *
                                sharpnessIntensity / (float) 5000);
            }
        }
        return output;
    }

    //Returns a new pixel for which each of the original color intensities are multiplied by sharpnessIntensity + 1.
    private int lighten(int pixel, float sharpnessIntensity){
        return makePixel(limitColor((int) (getRed(pixel) + sharpnessIntensity * getRed(pixel))),
                limitColor((int) (getGreen(pixel) + sharpnessIntensity * getGreen(pixel))),
                limitColor((int) (getBlue(pixel) + sharpnessIntensity * getBlue(pixel))));
    }

    //Returns an integer bounded below at 0 and above by 255.
    private int limitColor(int color){
        if (color > 255){
            return 255;
        } else if (color < 0) {
            return 0;
        }
        return color;
    }

    //Returns the average intensity of the input pixel.
    private int averageIntensity(int pixel) {
        return (getRed(pixel) + getGreen(pixel) + getBlue(pixel)) / 3;
    }
}

/*
Each pixel is replaced with a light pixel if it came from a neighborhood of high contrast,
or replaced with a dark pixel if it came from a neighborhood of low contrast.
 */
class HighlightEdges extends ImageEffect{
    public HighlightEdges() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be highlighted.\n" +
                        "Minimum Neighborhood Radius: 1\n" +
                        "Default Neighborhood Radius: 3\n" +
                        "Maximum Neighborhood Radius: 3",
                3, 1, 3));
        params.add(new ImageEffectParam("Highlight Intensity",
                "Set how dark pixels from low contrast neighborhoods should get,\n" +
                        "and how light pixels from high contrast neighborhoods should get.\n" +
                        "Minimum Highlight Intensity: 1\n" +
                        "Default Highlight Intensity: 100\n" +
                        "Maximum Highlight Intensity: 500",
                100, 1, 500));
        params.add(new ImageEffectParam("Threshold",
                "Set the threshold difference of pixel and neighborhood intensity\n" +
                        "at which pixels cease to be lighter and start to be darker.\n" +
                        "Minimum Threshold: 1\n" +
                        "Default Threshold: 20\n" +
                        "Maximum Threshold: 100",
                20, 1, 100));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int[][] output = new int[pixels.length][pixels[0].length];

        //neighborhoodRadius is the radius for the neighborhood to be highlighted.
        final int neighborhoodRadius = params.get(0).getValue();

        //highlightIntensity is how dark pixels from low contrast neighborhoods should get,
        //and how light pixels from high contrast neighborhoods should get.
        final int highlightIntensity = params.get(1).getValue();

        //threshold is the threshold difference of pixel and neighborhood intensity
        //at which pixels cease to be lighter and start to be darker.
        final int threshold = params.get(2).getValue();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //averageIntensity is the average intensity of every pixel within the neighborhood
                int averageIntensity = 0;

                int count = 0;

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {
                            averageIntensity += averageIntensity(pixels[row + i][col + j]);
                            count++;
                        }
                    }
                }
                averageIntensity /= count;

                //Lighten the original pixel according to the absolute value of the
                //difference between the average intensity of the neighborhood and of
                //the pixel, minus the threshold, and according to a factor of the
                //sharpnessIntensity. This new pixel is assigned to the corresponding
                //pixel in output.
                output[row][col] = lighten(pixels[row][col],
                        (Math.abs(averageIntensity(pixels[row][col]) - averageIntensity) - threshold) *
                                highlightIntensity / (float) 5000);
            }
        }
        return output;
    }

    //Returns a new pixel for which each of the original color intensities are multiplied by sharpnessIntensity + 1.
    private int lighten(int pixel, float highlightIntensity){
        return makePixel(limitColor((int) (getRed(pixel) + highlightIntensity * getRed(pixel))),
                limitColor((int) (getGreen(pixel) + highlightIntensity * getGreen(pixel))),
                limitColor((int) (getBlue(pixel) + highlightIntensity * getBlue(pixel))));
    }

    //Returns an integer bounded below at 0 and above by 255.
    private int limitColor(int color){
        if (color > 255){
            return 255;
        } else if (color < 0) {
            return 0;
        }
        return color;
    }

    //Returns the average intensity of the input pixel.
    private int averageIntensity(int pixel) {
        return (getRed(pixel) + getGreen(pixel) + getBlue(pixel)) / 3;
    }
}

/*
Replace each pixel with the median pixel value of a small neighborhood.
 */
class DeNoise extends ImageEffect{
    public DeNoise() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be analyzed.\n" +
                        "Minimum Neighborhood Radius: 1\n" +
                        "Default Neighborhood Radius: 1\n" +
                        "Maximum Neighborhood Radius: 3",
                1, 1, 3));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int[][] output = new int[pixels.length][pixels[0].length];

        //neighborhoodRadius is the radius for the neighborhood to be analyzed.
        final int neighborhoodRadius = params.get(0).getValue();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //redValues, greenValues, and blueValues are ArrayLists that store
                //the value of each color intensity in each pixel in the neighborhood
                ArrayList<Integer> redValues = new ArrayList<Integer>();
                ArrayList<Integer> greenValues = new ArrayList<Integer>();
                ArrayList<Integer> blueValues = new ArrayList<Integer>();

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {

                            int thisPixel = pixels[row + i][col + j];
                            redValues.add(getRed(thisPixel));
                            greenValues.add(getGreen(thisPixel));
                            blueValues.add(getBlue(thisPixel));
                        }
                    }
                }

                //Assign the median of each color in the neighborhood onto the corresponding pixel in output
                output[row][col] = makePixel(getMedianValue(redValues),
                        getMedianValue(greenValues),
                        getMedianValue(blueValues));
            }
        }
        return output;
    }

    //Use recursion to return the median value of an ArrayList of Integers
    private int getMedianValue(ArrayList<Integer> input){
        if(input.size() == 0) {
            return 0;
        } else if(input.size() == 1){
            return input.get(0);
        } else if (input.size() == 2) {
            return (input.get(0) + input.get(1)) / 2;
        }
        int min = input.get(0);
        int minIndex = 0;
        int max = input.get(0);
        int maxIndex = 0;
        ArrayList<Integer> output = new ArrayList<Integer>();
        for(int i = 0; i < input.size(); i++){
            output.add(input.get(i));
            if(input.get(i) < min){
                min = input.get(i);
                minIndex = i;
            } else if(input.get(i) > max){
                max = input.get(i);
                maxIndex = i;
            }
        }
        output.remove(minIndex);
        if (minIndex < maxIndex) {
            output.remove(maxIndex - 1);
        } else {
            output.remove(maxIndex);
        }
        return getMedianValue(output);
    }
}

/*
Replace each pixel with the minimum pixel value of a small neighborhood.
 */
class Erode extends ImageEffect{
    public Erode() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be highlighted.\n" +
                        "Minimum Neighborhood Radius: 1\n" +
                        "Default Neighborhood Radius: 1\n" +
                        "Maximum Neighborhood Radius: 3",
                1, 1, 3));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int[][] output = new int[pixels.length][pixels[0].length];

        //neighborhoodRadius is the radius for the neighborhood to be analyzed.
        final int neighborhoodRadius = params.get(0).getValue();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //minRed, minGreen, and minBlue are the minimum intensities of the corresponding color in the neighborhood.
                int minRed = getRed(pixels[row][col]);
                int minGreen = getGreen(pixels[row][col]);
                int minBlue = getBlue(pixels[row][col]);

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {

                            int thisPixel = pixels[row + i][col + j];
                            if(getRed(thisPixel) < minRed) {
                                minRed = getRed(thisPixel);
                            }
                            if(getGreen(thisPixel) < minGreen) {
                                minGreen = getGreen(thisPixel);
                            }
                            if(getBlue(thisPixel) < minBlue) {
                                minBlue = getBlue(thisPixel);
                            }
                        }
                    }
                }

                //Assign the minimum of each color in the neighborhood onto the corresponding pixel in output
                output[row][col] = makePixel(minRed, minGreen, minBlue);
            }
        }
        return output;
    }

}

class Dilate extends ImageEffect{
    public Dilate() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be highlighted.\n" +
                        "Minimum Neighborhood Radius: 1\n" +
                        "Default Neighborhood Radius: 1\n" +
                        "Maximum Neighborhood Radius: 3",
                1, 1, 3));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int[][] output = new int[pixels.length][pixels[0].length];

        //neighborhoodRadius is the radius for the neighborhood to be analyzed.
        final int neighborhoodRadius = params.get(0).getValue();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //maxRed, maxGreen, and maxBlue are the minimum intensities of the corresponding color in the neighborhood.
                int maxRed = getRed(pixels[row][col]);
                int maxGreen = getGreen(pixels[row][col]);
                int maxBlue = getBlue(pixels[row][col]);

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {

                            int thisPixel = pixels[row + i][col + j];
                            if(getRed(thisPixel) > maxRed) {
                                maxRed = getRed(thisPixel);
                            }
                            if(getGreen(thisPixel) > maxGreen) {
                                maxGreen = getGreen(thisPixel);
                            }
                            if(getBlue(thisPixel) > maxBlue) {
                                maxBlue = getBlue(thisPixel);
                            }
                        }
                    }
                }

                //Assign the maximum of each color in the neighborhood onto the corresponding pixel in output
                output[row][col] = makePixel(maxRed, maxGreen, maxBlue);
            }
        }
        return output;
    }

}


/*
Turn a color image into a black and white image, with a red tint in areas where red intensity is greatest.
 */
class BlackAndWhiteWithRedTint extends ImageEffect {
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Compute the average intensity of the three colors.
                int averageIntensity = (getRed(thisPixel) + getGreen(thisPixel) + getBlue(thisPixel)) / 3;

                if(getRed(thisPixel) >= averageIntensity) {

                    //This pixel will have a red tint
                    pixels[row][col] = makePixel(getRed(thisPixel), averageIntensity, averageIntensity);
                } else {

                    //Set red, green, and blue to the average intensity of the pixel.
                    //This pixel will be a grayscale pixel.
                    pixels[row][col] = makePixel(averageIntensity, averageIntensity, averageIntensity);
                }
            }
        }
        return pixels;
    }
}

/*
Turn a color image into a black and white image, with a green tint in areas where green intensity is greatest.
 */
class BlackAndWhiteWithGreenTint extends ImageEffect {
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Compute the average intensity of the three colors.
                int averageIntensity = (getRed(thisPixel) + getGreen(thisPixel) + getBlue(thisPixel)) / 3;

                if(getGreen(thisPixel) >= averageIntensity) {

                    //This pixel will have a green tint
                    pixels[row][col] = makePixel(averageIntensity, getGreen(thisPixel), averageIntensity);
                } else {

                    //Set red, green, and blue to the average intensity of the pixel.
                    //This pixel will be a grayscale pixel.
                    pixels[row][col] = makePixel(averageIntensity, averageIntensity, averageIntensity);
                }
            }
        }
        return pixels;
    }
}

/*
Turn a color image into a black and white image, with a blue tint in areas where blue intensity is greatest.
 */
class BlackAndWhiteWithBlueTint extends ImageEffect {
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Compute the average intensity of the three colors.
                int averageIntensity = (getRed(thisPixel) + getGreen(thisPixel) + getBlue(thisPixel)) / 3;

                if(getBlue(thisPixel) >= averageIntensity) {

                    //This pixel will have a red tint
                    pixels[row][col] = makePixel(averageIntensity, averageIntensity, getBlue(thisPixel));
                } else {

                    //Set red, green, and blue to the average intensity of the pixel.
                    //This pixel will be a grayscale pixel.
                    pixels[row][col] = makePixel(averageIntensity, averageIntensity, averageIntensity);
                }
            }
        }
        return pixels;
    }
}

/*
Turn a color image into a black and white image, with a tint in areas where specific color intensity is greatest.
 */
class BlackAndWhiteWithRGBTint extends ImageEffect {
    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){
                int thisPixel = pixels[row][col];

                //Compute the average intensity of the three colors.
                int averageIntensity = (getRed(thisPixel) + getGreen(thisPixel) + getBlue(thisPixel)) / 3;

                if(getRed(thisPixel) > getGreen(thisPixel) &&
                        getRed(thisPixel) > getBlue(thisPixel)) {

                    //This pixel will have a red tint
                    pixels[row][col] = makePixel(getRed(thisPixel), averageIntensity, averageIntensity);

                } else if(getGreen(thisPixel) > getRed(thisPixel) &&
                        getGreen(thisPixel) > getBlue(thisPixel)) {

                    //This pixel will have a green tint
                    pixels[row][col] = makePixel(averageIntensity, getGreen(thisPixel), averageIntensity);

                } else if(getBlue(thisPixel) > getRed(thisPixel) &&
                        getBlue(thisPixel) > getGreen(thisPixel)) {

                    //This pixel will have a blue tint
                    pixels[row][col] = makePixel(averageIntensity, averageIntensity, getBlue(thisPixel));
                } else {

                    //Set red, green, and blue to the average intensity of the pixel.
                    //This pixel will be a grayscale pixel.
                    pixels[row][col] = makePixel(averageIntensity, averageIntensity, averageIntensity);
                }
            }
        }
        return pixels;
    }
}

/*
Trace dark pixels in high contrast to be pure black, similar to an ink drawing.
 */
class Trace extends ImageEffect{
    public Trace() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be traced.\n" +
                        "Minimum Neighborhood Radius: 1\n" +
                        "Default Neighborhood Radius: 3\n" +
                        "Maximum Neighborhood Radius: 3",
                3, 1, 3));
        params.add(new ImageEffectParam("Threshold",
                "Set the threshold difference of pixel and neighborhood intensity\n" +
                        "at which pixels are traced.\n" +
                        "Minimum Threshold: 1\n" +
                        "Default Threshold: 20\n" +
                        "Maximum Threshold: 100",
                20, 1, 100));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int[][] output = new int[pixels.length][pixels[0].length];

        //neighborhoodRadius is the radius for the neighborhood to be traced.
        final int neighborhoodRadius = params.get(0).getValue();

        //threshold is the threshold difference of pixel and neighborhood intensity
        //at which pixels are traced.
        final int threshold = params.get(1).getValue();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //averageIntensity is the average intensity of every pixel within the neighborhood
                int averageIntensity = 0;

                int count = 0;

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {

                            averageIntensity += averageIntensity(pixels[row + i][col + j]);
                            count++;
                        }
                    }
                }
                averageIntensity /= count;

                //If the selected pixel is dark enough relative to its neighborhood, it is outputted as black.
                if(averageIntensity - averageIntensity(pixels[row][col]) >= threshold) {

                    output[row][col] = makePixel(0, 0, 0);

                    //Else, it is outputted as white.
                } else {
                    output[row][col] = makePixel(255, 255, 255);
                }
            }
        }
        return output;
    }

    //Returns the average intensity of the input pixel.
    private int averageIntensity(int pixel) {
        return (getRed(pixel) + getGreen(pixel) + getBlue(pixel)) / 3;
    }
}

/*
Trace dark pixels in high contrast to be pure black, similar to an ink drawing, over the original image.
 */
class TraceOverImage extends ImageEffect{
    public TraceOverImage() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be traced.\n" +
                        "Minimum Neighborhood Radius: 1\n" +
                        "Default Neighborhood Radius: 3\n" +
                        "Maximum Neighborhood Radius: 3",
                3, 1, 3));
        params.add(new ImageEffectParam("Threshold",
                "Set the threshold difference of pixel and neighborhood intensity\n" +
                        "at which pixels are traced.\n" +
                        "Minimum Threshold: 1\n" +
                        "Default Threshold: 20\n" +
                        "Maximum Threshold: 100",
                20, 1, 100));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int[][] output = new int[pixels.length][pixels[0].length];

        //neighborhoodRadius is the radius for the neighborhood to be traced.
        final int neighborhoodRadius = params.get(0).getValue();

        //threshold is the threshold difference of pixel and neighborhood intensity
        //at which pixels are traced.
        final int threshold = params.get(1).getValue();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //averageIntensity is the average intensity of every pixel within the neighborhood
                int averageIntensity = 0;

                output[row][col] = pixels[row][col];
                int count = 0;

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {

                            averageIntensity += averageIntensity(pixels[row + i][col + j]);
                            count++;
                        }
                    }
                }
                averageIntensity /= count;

                //If the selected pixel is dark enough relative to its neighborhood, it is outputted as black.
                if(averageIntensity - averageIntensity(pixels[row][col]) >= threshold) {

                    output[row][col] = makePixel(0, 0, 0);
                }
            }
        }
        return output;
    }

    //Returns the average intensity of the input pixel.
    private int averageIntensity(int pixel) {
        return (getRed(pixel) + getGreen(pixel) + getBlue(pixel)) / 3;
    }
}

/*
Trace dark pixels in high contrast to be intensified, similar to an colored ink drawing.
 */
class TraceRGB extends ImageEffect{
    public TraceRGB() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be traced.\n" +
                        "Minimum Neighborhood Radius: 1\n" +
                        "Default Neighborhood Radius: 3\n" +
                        "Maximum Neighborhood Radius: 3",
                3, 1, 3));
        params.add(new ImageEffectParam("Threshold",
                "Set the threshold difference of pixel and neighborhood intensity\n" +
                        "at which pixels are traced.\n" +
                        "Minimum Threshold: 1\n" +
                        "Default Threshold: 20\n" +
                        "Maximum Threshold: 100",
                20, 1, 100));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int[][] output = new int[pixels.length][pixels[0].length];

        //neighborhoodRadius is the radius for the neighborhood to be traced.
        final int neighborhoodRadius = params.get(0).getValue();

        //threshold is the threshold difference of pixel and neighborhood intensity
        //at which pixels are traced.
        final int threshold = params.get(1).getValue();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //averageIntensity is the average intensity of every pixel within the neighborhood
                int averageIntensity = 0;

                int count = 0;

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {

                            averageIntensity += averageIntensity(pixels[row + i][col + j]);
                            count++;
                        }
                    }
                }
                averageIntensity /= count;

                //If the selected pixel is dark enough relative to its neighborhood, it is intensified.
                if(averageIntensity - averageIntensity(pixels[row][col]) >= threshold) {
                    output[row][col] = intensify(pixels[row][col]);

                    //Else, it is outputted as black.
                } else {
                    output[row][col] = makePixel(0, 0, 0);
                }
            }
        }
        return output;
    }

    //Returns the average intensity of the input pixel.
    private int averageIntensity(int pixel) {
        return (getRed(pixel) + getGreen(pixel) + getBlue(pixel)) / 3;
    }

    //Returns an intensified version of the input pixel.
    private int intensify(int pixel){
        int maxIntensity = Math.max(Math.max(getRed(pixel), getGreen(pixel)), getBlue(pixel));
        float intensityFactor = (float) 254 / maxIntensity;
        if((int) maxIntensity * intensityFactor > 255) {
            System.out.println("error");
        }
        return makePixel((int) (intensityFactor * getRed(pixel)), (int) (intensityFactor * getGreen(pixel)), (int) (intensityFactor * getBlue(pixel)));
    }
}

/*
Trace dark pixels in high contrast to be intensified, similar to an colored ink drawing, over the original image.
 */
class TraceRGBOverImage extends ImageEffect{
    public TraceRGBOverImage() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be traced.\n" +
                        "Minimum Neighborhood Radius: 1\n" +
                        "Default Neighborhood Radius: 3\n" +
                        "Maximum Neighborhood Radius: 3",
                3, 1, 3));
        params.add(new ImageEffectParam("Threshold",
                "Set the threshold difference of pixel and neighborhood intensity\n" +
                        "at which pixels are traced.\n" +
                        "Minimum Threshold: 1\n" +
                        "Default Threshold: 20\n" +
                        "Maximum Threshold: 100",
                20, 1, 100));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int[][] output = new int[pixels.length][pixels[0].length];

        //neighborhoodRadius is the radius for the neighborhood to be traced.
        final int neighborhoodRadius = params.get(0).getValue();

        //threshold is the threshold difference of pixel and neighborhood intensity
        //at which pixels are traced.
        final int threshold = params.get(1).getValue();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //averageIntensity is the average intensity of every pixel within the neighborhood
                int averageIntensity = 0;

                output[row][col] = pixels[row][col];
                int count = 0;

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {

                            averageIntensity += averageIntensity(pixels[row + i][col + j]);
                            count++;
                        }
                    }
                }
                averageIntensity /= count;

                //If the selected pixel is dark enough relative to its neighborhood, it is intensified.
                if(averageIntensity - averageIntensity(pixels[row][col]) >= threshold) {
                    output[row][col] = intensify(pixels[row][col]);
                }
            }
        }
        return output;
    }

    //Returns the average intensity of the input pixel.
    private int averageIntensity(int pixel) {
        return (getRed(pixel) + getGreen(pixel) + getBlue(pixel)) / 3;
    }

    //Returns an intensified version of the input pixel.
    private int intensify(int pixel){
        int maxIntensity = Math.max(Math.max(getRed(pixel), getGreen(pixel)), getBlue(pixel));
        float intensityFactor = (float) 254 / maxIntensity;
        if((int) maxIntensity * intensityFactor > 255) {
            System.out.println("error");
        }
        return makePixel((int) (intensityFactor * getRed(pixel)), (int) (intensityFactor * getGreen(pixel)), (int) (intensityFactor * getBlue(pixel)));
    }
}

/*
Trace dark pixels in high contrast to be pure black, similar to an ink drawing, and tint the rest of the image to give a watercolor effect.
 */
class TraceWithRGBTint extends ImageEffect{
    public TraceWithRGBTint() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Radius",
                "Set a radius for the neighborhood to be traced.\n" +
                        "Minimum Neighborhood Radius: 1\n" +
                        "Default Neighborhood Radius: 3\n" +
                        "Maximum Neighborhood Radius: 3",
                3, 1, 3));
        params.add(new ImageEffectParam("Threshold",
                "Set the threshold difference of pixel and neighborhood intensity\n" +
                        "at which pixels are traced.\n" +
                        "Minimum Threshold: 1\n" +
                        "Default Threshold: 20\n" +
                        "Maximum Threshold: 100",
                20, 1, 100));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){
        int[][] output = new int[pixels.length][pixels[0].length];

        //neighborhoodRadius is the radius for the neighborhood to be traced.
        final int neighborhoodRadius = params.get(0).getValue();

        //threshold is the threshold difference of pixel and neighborhood intensity
        //at which pixels are traced.
        final int threshold = params.get(1).getValue();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[row].length; col++){

                //averageIntensity is the average intensity of every pixel within the neighborhood
                int averageIntensity = 0;

                int count = 0;

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = -neighborhoodRadius; i <= neighborhoodRadius; i++){
                    for(int j = -neighborhoodRadius; j <= neighborhoodRadius; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(0 <= row + i && row + i < pixels.length &&
                                0 <= col + j && col + j < pixels[row].length) {

                            averageIntensity += averageIntensity(pixels[row + i][col + j]);
                            count++;
                        }
                    }
                }
                averageIntensity /= count;
                int thisPixel = pixels[row][col];
                if(getRed(thisPixel) > getGreen(thisPixel) &&
                        getRed(thisPixel) > getBlue(thisPixel)) {

                    //This pixel will have a red tint
                    output[row][col] = makePixel(getRed(thisPixel), averageIntensity, averageIntensity);

                } else if(getGreen(thisPixel) > getRed(thisPixel) &&
                        getGreen(thisPixel) > getBlue(thisPixel)) {

                    //This pixel will have a green tint
                    output[row][col] = makePixel(averageIntensity, getGreen(thisPixel), averageIntensity);

                } else if(getBlue(thisPixel) > getRed(thisPixel) &&
                        getBlue(thisPixel) > getGreen(thisPixel)) {

                    //This pixel will have a blue tint
                    output[row][col] = makePixel(averageIntensity, averageIntensity, getBlue(thisPixel));
                } else {

                    //Set red, green, and blue to the average intensity of the pixel.
                    //This pixel will be a grayscale pixel.
                    output[row][col] = makePixel(averageIntensity, averageIntensity, averageIntensity);
                }
                //If the selected pixel is dark enough relative to its neighborhood, it is outputted as black.
                if(averageIntensity - averageIntensity(pixels[row][col]) >= threshold) {

                    output[row][col] = makePixel(0, 0, 0);

                    //Else, it is outputted as white.
                }
            }
        }
        return output;
    }

    //Returns the average intensity of the input pixel.
    private int averageIntensity(int pixel) {
        return (getRed(pixel) + getGreen(pixel) + getBlue(pixel)) / 3;
    }
}

/*
Pixelate the original image according to the specified neighborhood size;
 */
class Pixelate extends ImageEffect{
    public Pixelate() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Neighborhood Diameter",
                "Set a diameter for the neighborhood to be pixelated.\n" +
                        "Minimum Neighborhood Radius: 2\n" +
                        "Default Neighborhood Radius: 6\n" +
                        "Maximum Neighborhood Radius: 10",
                6, 2, 10));
    }

    public int[][] apply(int[][] pixels, ArrayList<ImageEffectParam> params){

        //neighborhoodDiameter is the diameter for the neighborhood to be pixelated.
        final int neighborhoodDiameter = params.get(0).getValue();

        int[][] output = new int[pixels.length][pixels[0].length];
        for(int row = 0; row < pixels.length / neighborhoodDiameter; row++){
            for(int col = 0; col < pixels[0].length / neighborhoodDiameter; col++){
                int originalRow = row;
                int originalCol = col;
                row *= neighborhoodDiameter;
                col *= neighborhoodDiameter;
                int count = 0;

                //averageRed, averageGreen, and averageBlue represent the average color intensities of the neighborhood.
                int averageRed = 0;
                int averageGreen = 0;
                int averageBlue = 0;

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = 0; i < neighborhoodDiameter; i++){
                    for(int j = 0; j < neighborhoodDiameter; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(row + i < pixels.length && col + j < pixels[row].length) {

                            int thisPixel = pixels[row + i][col + j];
                            averageRed += getRed(thisPixel);
                            averageGreen += getGreen(thisPixel);
                            averageBlue += getBlue(thisPixel);
                            count++;
                        }
                    }
                }
                averageRed /= count;
                averageGreen /= count;
                averageBlue /= count;

                //i and j iterate in a nested for loop to evaluate each pixel within the specified neighborhood.
                for(int i = 0; i < neighborhoodDiameter; i++){
                    for(int j = 0; j < neighborhoodDiameter; j++){

                        //Test to determine whether each pixel is within the bounds of the image.
                        if(row + i < pixels.length && col + j < pixels[row].length) {

                            output[row + i][col + j] = makePixel(averageRed, averageGreen, averageBlue);
                        }
                    }
                }
                row = originalRow;
                col = originalCol;
            }
        }
        return output;
    }
}

class Invert extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = ~pixels[y][x];
            }
        }
        return pixels;
    }
}

class Dummy extends ImageEffect {

    public Dummy() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("ParamName",
                                           "Description of param.",
                                           10, 0, 1000));
    }

    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        // Use params here.
        return pixels;
    }
}
