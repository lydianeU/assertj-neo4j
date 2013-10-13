/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright 2013-2013 the original author or authors.
 */
package org.assertj.neo4j.api.node;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

import static org.assertj.neo4j.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Checks <code>{@link org.assertj.neo4j.api.NodeAssert#hasNotLabel(String)}</code> behavior.
 * 
 * @author Florent Biville
 */
public class NodeAssert_hasNotLabel_string_Test {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private Node node = mock(Node.class);

  @Test
  public void should_pass_hasNotLabel_if_node_has_not_label() {
    given_node_with_label("MAMMAL");

    assertThat(node).hasNotLabel("REPTILE");
  }

  @Test
  public void should_fail_hasNotLabel_if_node_is_null() {
    expectedException.expect(AssertionError.class);
    expectedException.expectMessage("Expecting actual not to be null");

    assertThat((Node) null).hasNotLabel("LABEL");
  }

  @Test
  public void should_fail_hasNotLabel_if_label_value_is_null() {
    expectedException.expect(IllegalArgumentException.class);

    assertThat(node).hasNotLabel((String) null);
  }

  @Test
  public void should_fail_hasNotLabel_if_node_does_have_label() {
    expectedException.expect(AssertionError.class);

    given_node_with_label("REPTILE");

    assertThat(node).hasNotLabel("REPTILE");
  }

  private void given_node_with_label(String label) {
    ResourceIterable labels = mock(ResourceIterable.class);
    ResourceIterator iterator = mock(ResourceIterator.class);
    when(iterator.next()).thenReturn(DynamicLabel.label(label));
    when(iterator.hasNext()).thenReturn(true, false);
    when(labels.iterator()).thenReturn(iterator);
    when(node.getLabels()).thenReturn(labels);
    GraphDatabaseService graph = mock(GraphDatabaseService.class);
    when(graph.beginTx()).thenReturn(mock(Transaction.class));
    when(node.getGraphDatabase()).thenReturn(graph);
  }

}