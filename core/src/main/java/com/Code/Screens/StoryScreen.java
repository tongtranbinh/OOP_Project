package com.Code.Screens;

import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class StoryScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private Skin skin;
    private TextButton backButton;

    private String[] storyParts;  // Cốt truyện được chia thành các đoạn
    private int currentPart;      // Chỉ số đoạn hiện tại

    public StoryScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        // Tạo mảng storyParts để chia cốt truyện thành từng phần
        this.storyParts = new String[] {
            "Bổ sung vào dòng thời gian phụ:\n" +
                "Nơi mà Crimson Moon ngủ đông hình thành nên một khu rừng máu và sinh lực của vị thần này tạo nên, được đặt theo đúng tên của vị thần này (Crimson Moon). " +
                "Ở khắp trong khu rừng là những loài động vật bị máu của Vị Cổ Thần làm cho điên loạn, khiến khu vực này trở nên vô cùng nguy hiểm, nhưng cũng là nơi duy nhất mà các tổ chức có thể " +
                "tìm hiểu về các vị thần. HUST gọi khu vực này là X-37 (?).\n" +
                "\n" +
                "Sau khi cuộc chiến của con người và vị thần diễn ra, một vị Cổ Thần khác – The Elder God of Death (Vị cổ thần của cái chết) bí mật tới Earth – 2407 " +
                "với mục tiêu đoạt lấy sức mạnh của Crimson Moon, nhằm tạo ra một đội quân Undead kết hợp với máu của vị Cổ Thần này, tiêu diệt toàn bộ nhân loại và tạo ra các thế giới chỉ bao gồm các vị thần.",

            "Vào dòng thời gian chính:\nNhân vật chính (tên người chơi nhập) - Học giả tại tổ chức HUST, đồng thời là chiến binh xuất sắc nhất, dưới sự hướng dẫn của Master William, " +
                "được chọn ra để nghiên cứu về con người và thánh thần, với mục tiêu kết hợp con người và thần linh nhằm đưa con người tiến hóa lên một tầm cao mới.\n" +
                "Tổ chức HUST nghiên cứu về thánh thần và con người có mục tiêu đối nghịch với những kẻ tôn thờ Celestial Flame, tổ chức này muốn xóa sổ các vị thần và đẩy con người vào một " +
                "thế giới không có thần linh.\nNhân vật chính sẽ đi đến khu vực X-37 để thu thập thông tin về vị Cổ Thần của Máu.",

            "Các địa điểm trong khu vực X-37:\n" +
                "+) Hall Of Sins (Sảnh đường của tội lỗi)\n" +
                "+) Khu vực đánh boss (The Elder God of Death)\n" +
                "+) Albedo – The Lost City Of Dreams\n" +
                "+) The Graveyard of Mankind.\n" +
                "Nhân vật chính bắt đầu hành trình của mình từ The Graveyard of Mankind, đối đầu với các quái vật điên loạn, đánh bại undead đầu tiên tại đây.",

            "Gặp boss đầu tiên: The Prayer Of Sins. Thực thể này do The Elder God of Death tạo ra. Thực thể này vô cùng mạnh, nhân vật chính không thể đánh bại, " +
                "nhưng nếu đánh bại được, sẽ nhận được phần thưởng và được teleport ngay về bệnh viện của HUST.\n" +
                "Sau khi quay về, nhân vật chính và Master William nghi ngờ về sự xuất hiện của các undead, điều này là một phát hiện mới chưa được ghi chép trong tài liệu của HUST.",

            "HUST lập một đội đặc biệt, dưới sự dẫn dắt của nhân vật chính, để tiến sâu vào khu vực X-37, tìm hiểu thêm về vị Cổ Thần của Máu.\n" +
                "Đến khu vực Sảnh Đường Tội Lỗi, nhân vật chính phát hiện nơi đây là khu nghiên cứu của HUST, nơi đã thực hiện các thí nghiệm ghê rợn nhằm kết hợp máu Crimson Moon với con người.\n" +
                "Nhân vật chính phát hiện mình là thành công duy nhất của các thí nghiệm này và là một demi-god (Con cái duy nhất của Crimson Moon).",

            "Sau khi trở về HUST, nhân vật chính gặp Kafka, một thí nghiệm bị bỏ rơi bởi tổ chức, nhưng sau đó gia nhập Celestial Flame.\n" +
                "Kafka giải thích rằng HUST thực chất là một tổ chức tàn ác, đã che giấu sự thật về các thí nghiệm của mình và tuyên truyền sự tẩy trắng về Celestial Flame. " +
                "Nhân vật chính quyết định đi tìm hiểu thêm và khám phá bí mật đằng sau các undead do The Elder God of Death tạo ra, đồng thời khẳng định sự độc ác của HUST."
        };
        this.currentPart = 0;  // Bắt đầu từ phần đầu tiên
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));  // Cung cấp skin cho các nút

        // Tạo nút "Back" để quay lại Menu
        backButton = new TextButton("Back", skin);
        backButton.setPosition(300, 50);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));  // Quay lại Menu khi nhấn Back
            }
        });

        // Thêm nút vào stage
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  // Xóa màn hình

        batch.begin();
        font.draw(batch, storyParts[currentPart], 50, 400);  // Hiển thị cốt truyện

        batch.end();

        // Kiểm tra nếu người chơi nhấn Enter, chuyển sang phần tiếp theo của cốt truyện
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            currentPart++;
            if (currentPart >= storyParts.length) {
                currentPart = storyParts.length - 1;  // Dừng lại ở phần cuối cùng
            }
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }
}
