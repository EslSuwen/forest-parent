package cn.one2rich.forest.util;

import cn.hutool.core.io.FileUtil;
import cn.one2rich.forest.dto.ArticleLucene;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import java.io.IOException;
import java.util.Arrays;

/**
 * 文章索引更新工具类
 *
 * @author suwen
 */
public class ArticleIndexUtil {

  /** lucene索引保存目录 */
  private static final String PATH = System.getProperty("user.dir") + "/cn/one2rich/forest/lucene/index";

  /** 系统运行时索引保存目录 */
  private static final String INDEX_PATH =
      System.getProperty("user.dir") + "/cn/one2rich/forest/lucene/index/index777";

  /** 删除所有运行中保存的索引 */
  public static void deleteAllIndex() {
    if (FileUtil.exist(INDEX_PATH)) {
      FileUtil.del(INDEX_PATH);
    }
  }

  public static void addIndex(ArticleLucene t) {
    creatIndex(t);
  }

  public static void updateIndex(ArticleLucene t) {
    deleteIndex(t.getIdArticle());
    creatIndex(t);
  }

  /**
   * 增加或创建单个索引
   *
   * @param t
   * @throws Exception
   */
  private static synchronized void creatIndex(ArticleLucene t) {
    System.out.println("创建单个索引");
    IndexWriter writer;
    try {
      writer = IndexUtil.getIndexWriter(INDEX_PATH, false);
      Document doc = new Document();
      doc.add(new StringField("id", t.getIdArticle() + "", Field.Store.YES));
      doc.add(new TextField("title", t.getArticleTitle(), Field.Store.YES));
      doc.add(new TextField("summary", t.getArticleContent(), Field.Store.YES));
      writer.addDocument(doc);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 删除单个索引 */
  public static synchronized void deleteIndex(String id) {
    Arrays.stream(FileUtil.ls(PATH))
        .forEach(
            each -> {
              if (each.isDirectory()) {
                IndexWriter writer;
                try {
                  writer = IndexUtil.getIndexWriter(each.getAbsolutePath(), false);
                  writer.deleteDocuments(new Term("id", id));
                  writer.forceMergeDeletes(); // 强制删除
                  writer.commit();
                  writer.close();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            });
  }
}
