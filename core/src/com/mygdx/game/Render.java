package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Render {
    private BitmapFont font;

    public Render(BitmapFont font) {
        this.font = font;
    }

    void renderEndGame(SpriteBatch batch)
    {
        final String text = "The End";
        final GlyphLayout layout = new GlyphLayout(font, text);

        final float fontX =  (Gdx.graphics.getWidth() - layout.width) / 2;
        final float fontY = (Gdx.graphics.getHeight() + layout.height) / 2;

        font.setColor(Color.RED);
        font.draw(batch, text, fontX , fontY);
    }

    /*
    void renderPauseGame(SpriteBatch batch)
    {
        font.setColor(Color.YELLOW);
        font.draw(batch, "Have a break, have a KitKat, uhm I mean PAUSE", Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight() / 2);
    }*/
}
