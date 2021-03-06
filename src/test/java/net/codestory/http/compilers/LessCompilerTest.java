/**
 * Copyright (C) 2013 all@code-story.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package net.codestory.http.compilers;

import static java.lang.ClassLoader.*;
import static java.nio.charset.StandardCharsets.*;
import static net.codestory.http.io.InputStreams.*;
import static org.assertj.core.api.Assertions.*;

import java.io.*;
import java.nio.file.*;

import org.junit.*;
import org.junit.rules.*;

public class LessCompilerTest {
  private static LessCompiler lessCompiler = new LessCompiler();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void empty() throws IOException {
    String css = lessCompiler.compile(Paths.get("empty.less"), "");

    assertThat(css).isEqualTo("/*# sourceMappingURL=empty.css.map */\n");
  }

  @Test
  public void to_css() throws IOException {
    String css = lessCompiler.compile(Paths.get("file.less"), "body { h1 { color: red; } }");

    assertThat(css).isEqualTo("body h1 {\n  color: red;\n}\n/*# sourceMappingURL=file.css.map */\n");
  }

  @Test
  public void large_css() throws IOException {
    String css = lessCompiler.compile(Paths.get("file.less"), readString(getSystemResourceAsStream("less/style.less"), UTF_8));

    assertThat(css).isNotEmpty();
  }

  @Test
  public void invalid_file() throws IOException {
    thrown.expect(IOException.class);
    thrown.expectMessage("Unable to compile less");

    lessCompiler.compile(Paths.get("invalid.less"), "body body");
  }
}
