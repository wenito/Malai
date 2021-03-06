package org.malai.javafx.interaction.binding;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.action.Action;
import org.malai.javafx.binding.KeyNodeBinder;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestKeyNodeBinder extends TestNodeBinder<Canvas> {
	@Override
	public void start(Stage stage) {
		widget1 = new Canvas();
		widget2 = new Canvas();
		super.start(stage);
	}

	@BeforeEach
	void setUp() {
		WaitForAsyncUtils.waitForFxEvents();
		grabFocus(widget1);
	}

	@Test
	public void testNoActionExecutedOnNoKey() throws InstantiationException, IllegalAccessException {
		new KeyNodeBinder<>(StubAction.class, instrument).
			on(widget1).
			bind();
		type(KeyCode.C);
		assertEquals(1, instrument.exec.get());
		assertEquals(1, ((StubAction) instrument.lastCreatedAction).exec.get());
	}

	@Test
	public void testActionExecutedOnKey() throws InstantiationException, IllegalAccessException {
		new KeyNodeBinder<>(StubAction.class, instrument).
			on(widget1).
			with(KeyCode.C).
			bind();
		type(KeyCode.C);
		assertEquals(1, instrument.exec.get());
		assertEquals(1, ((StubAction) instrument.lastCreatedAction).exec.get());
	}

	@Test
	public void testActionExecutedOnTwoKeys() throws InstantiationException, IllegalAccessException {
		new KeyNodeBinder<>(StubAction.class, instrument).
			on(widget1).
			with(KeyCode.C, KeyCode.CONTROL).
			bind();
		press(KeyCode.CONTROL).type(KeyCode.C);
		assertEquals(1, instrument.exec.get());
		assertEquals(1, ((StubAction) instrument.lastCreatedAction).exec.get());
	}

	@Test
	public void testActionExecutedOnThreeKeys() throws InstantiationException, IllegalAccessException {
		new KeyNodeBinder<>(StubAction.class, instrument).
			on(widget1).
			with(KeyCode.C, KeyCode.CONTROL).
			bind();
		press(KeyCode.CONTROL).type(KeyCode.C).type(KeyCode.C);
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testNoActionExecutedOnTwoKeysReleased() throws InstantiationException, IllegalAccessException {
		new KeyNodeBinder<>(StubAction.class, instrument).
			on(widget1).
			with(KeyCode.C, KeyCode.CONTROL).
			bind();
		type(KeyCode.CONTROL).type(KeyCode.C);
		assertEquals(0, instrument.exec.get());
	}

	@Test
	public void testNoActionExecutedOnBadKey() throws InstantiationException, IllegalAccessException {
		new KeyNodeBinder<>(StubAction.class, instrument).
			on(widget1).
			with(KeyCode.C).
			bind();
		type(KeyCode.A);
		assertEquals(0, instrument.exec.get());
		assertNull(instrument.lastCreatedAction);
	}

	@Test
	public void testActionExecutedOnTwoCanvas() throws InstantiationException, IllegalAccessException {
		new KeyNodeBinder<>(StubAction.class, instrument).
			on(widget1, widget2).
			with(KeyCode.C).
			bind();
		grabFocus(widget2);
		type(KeyCode.C);
		assertEquals(1, instrument.exec.get());
		Action a = instrument.lastCreatedAction;
		assertEquals(1, ((StubAction) instrument.lastCreatedAction).exec.get());
		grabFocus(widget1);
		type(KeyCode.C);
		assertEquals(2, instrument.exec.get());
		assertEquals(1, ((StubAction) instrument.lastCreatedAction).exec.get());
		assertNotSame(a, instrument.lastCreatedAction);
	}

	@Test
	public void testInit1Executed() throws InstantiationException, IllegalAccessException {
		new KeyNodeBinder<>(StubAction.class, instrument).
			on(widget1).
			init(a -> a.exec.setValue(10)).
			bind();
		type(KeyCode.C);
		assertEquals(1, instrument.exec.get());
		assertEquals(11, ((StubAction) instrument.lastCreatedAction).exec.get());
	}

	@Test
	public void testInit2Executed() throws InstantiationException, IllegalAccessException {
		new KeyNodeBinder<>(StubAction.class, instrument).
			on(widget1).
			init((a, i) -> a.exec.setValue(20)).
			bind();
		type(KeyCode.C);
		assertEquals(1, instrument.exec.get());
		assertEquals(21, ((StubAction) instrument.lastCreatedAction).exec.get());
	}

	@Test
	public void testCheckFalse() throws InstantiationException, IllegalAccessException {
		new KeyNodeBinder<>(StubAction.class, instrument).
			on(widget1).
			when(i -> false).
			bind();
		type(KeyCode.C);
		assertEquals(0, instrument.exec.get());
	}
}
