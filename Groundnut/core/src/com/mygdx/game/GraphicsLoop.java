package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GraphicsLoop extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private int numPlayers = 4;
	private int numDimensions = 2;

	ShapeRenderer renderer;

	int[][] playerPositions = new int[4][2];


	//box2Dtestin

	int circleR = 10;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		renderer = new ShapeRenderer();



	}

	@Override
	public void render () {


		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		playerPositions = ClientListenerThread.getPositions();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.CYAN);
		renderer.circle(playerPositions[0][0], playerPositions[0][1], circleR);

		renderer.setColor(Color.GREEN);
		renderer.circle(playerPositions[1][0], playerPositions[1][1], circleR);

		renderer.setColor(Color.RED);
		renderer.circle(playerPositions[2][0], playerPositions[2][1], circleR);

		renderer.setColor(Color.PINK);
		renderer.circle(playerPositions[3][0], playerPositions[3][1], circleR);

		renderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}



}
