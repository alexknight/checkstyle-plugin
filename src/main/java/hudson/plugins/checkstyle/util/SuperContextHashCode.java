package hudson.plugins.checkstyle.util;

import hudson.plugins.analysis.util.ContextHashCode;
import hudson.plugins.analysis.util.EncodingValidator;
import org.apache.commons.io.LineIterator;

import java.io.IOException;

/**
 * 覆写原本的ContextHashCode计算方法
 */
public class SuperContextHashCode extends ContextHashCode {

    /** Number of lines before and after current line to consider. */
    private static final int LINES_LOOK_AHEAD = 3;
    private static final int BUFFER_SIZE = 1000;

    private StringBuilder context;

    /**
     * Creates a hash code from the source code of the warning line and the
     * surrounding context.
     *
     * @param fileName
     *            the absolute path of the file to read
     * @param line
     *            the line of the warning
     * @param encoding
     *            the encoding of the file, if <code>null</code> or empty then
     *            the default encoding of the platform is used
     * @return a has code of the source code
     * @throws IOException
     *             if the contents of the file could not be read
     */
    public int create(final String fileName, final int line, final String encoding) throws IOException {
        if (this.context == null) {
            readFileContext(fileName, line, encoding);
        }
        return this.context.toString().hashCode();
    }

    /**
     * 功能描述：获取原生的代码行上下文本内容
     * @param fileName 读取的文件名
     * @param line  问题行
     * @param encoding 编码
     * @throws IOException
     * @author <a href="mailto:zxf89949@alibaba-inc.com">张晓凡</a>
     * @create on 2016-10-27
     * @version 1.0.0
     */
    private void readFileContext(final String fileName, final int line, final String encoding) throws IOException {
        LineIterator lineIterator = EncodingValidator.readFile(fileName, encoding);
        this.context = new StringBuilder(BUFFER_SIZE);
        for (int i = 0; lineIterator.hasNext(); i++) {
            String currentLine = lineIterator.nextLine();
            if (i >= line - LINES_LOOK_AHEAD) {
                this.context.append(currentLine);
            }
            if (i > line + LINES_LOOK_AHEAD) {
                break;
            }
        }
        lineIterator.close();
    }

    public StringBuilder getContext() {
        return this.context;
    }

}
