/*
 * Copyright (c) 2016. Jan Wiemer
 */

package org.jacis;

import org.jacis.container.JacisContainer;
import org.jacis.container.JacisObjectTypeSpec;
import org.jacis.plugin.objectadapter.cloning.JacisCloningObjectAdapter;
import org.jacis.store.JacisStore;
import org.jacis.store.JacisStoreAdminInterface;
import org.jacis.testhelper.TestObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JacisContainerTest {

  private static final Logger log = LoggerFactory.getLogger(JacisContainerTest.class);

  @Test
  public void testCreateContainer() {
    JacisContainer container = new JacisContainer();
    assertNotNull(container);
    log.info("The JacisContainer: {}", container);
  }

  @Test
  public void testRegisterStore() {
    JacisContainer container = new JacisContainer();
    JacisObjectTypeSpec<String, TestObject, TestObject> objectTypeSpec = new JacisObjectTypeSpec<>(String.class, TestObject.class, new JacisCloningObjectAdapter<>());
    objectTypeSpec.setCheckViewsOnCommit(true);
    objectTypeSpec.setTrackOriginalValue(true);
    container.createStore(objectTypeSpec);
    JacisStoreAdminInterface<String,TestObject,TestObject> store = container.getStoreAdminInterface(String.class, TestObject.class);
    assertEquals(String.class, store.getObjectTypeSpec().getKeyClass());
    assertEquals(TestObject.class, store.getObjectTypeSpec().getValueClass());
    assertEquals(String.class, store.getObjectTypeSpec().getStoreIdentifier().getKeyClass());
    assertEquals(TestObject.class, store.getObjectTypeSpec().getStoreIdentifier().getValueClass());
    assertNotNull(store);
  }

  @Test
  public void testClearStores() {
    JacisContainer container = new JacisContainer();
    JacisObjectTypeSpec<String, TestObject, TestObject> objectTypeSpec = new JacisObjectTypeSpec<>(String.class, TestObject.class, new JacisCloningObjectAdapter<>());
    container.createStore(objectTypeSpec);
    JacisStore<String, TestObject> store = container.getStore(String.class, TestObject.class);
    container.clearAllStores();
    assertNotNull(store);
  }

}
