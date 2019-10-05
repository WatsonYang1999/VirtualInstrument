import javax.sound.midi.*;


/**
 *
 *    MiniMusic.java
 *
 *     功   能： TODO 
 *     类   名： MiniMusic.java
 *
 *  ver     変更日       角色    担当者     変更内容
 *     ──────────────────────────────────────────────
 *  V1.00   2013-3-19   模块    苏若年     初版
 *
 *     Copyright (c) 2013 dennisit corporation All Rights Reserved.
 *
 *  Email:<a href="mailto:DennisIT@163.com">发送邮件</a>
 *
 */
public class MiniMusic {

    public void play(){
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            Soundbank sb = synthesizer.getDefaultSoundbank();
            Instrument instruments[]= sb.getInstruments();
            MidiChannel[] channels = synthesizer.getChannels();
            MidiChannel channel = channels[0];
            System.out.println(instruments);
            int bass_index=0;

            for (Instrument i: instruments) {
                //System.out.println(i.getName());

                if(i.getName().equals("Electric Bass (finge"))
                    break;
                bass_index++;
            }
            System.out.println(bass_index);
            Instrument bass = instruments[bass_index];
            Sequencer player = MidiSystem.getSequencer();
            synthesizer.loadInstrument(bass);
            channel.programChange(bass_index);
            player.open();
            //节拍器
            Sequence seq = new Sequence(Sequence.PPQ,4);
            Track track = seq.createTrack();
            ShortMessage first = new ShortMessage();
            first.setMessage(192,bass_index,1,0);
            System.out.println("???");
            track.add(new MidiEvent(first,0));
            int base = 48;
            int whiteId[] =  {0,2,4,5,7,9,11};
            int someMusic[] = {5,6,1,2,3,3,2,3,3,3,2,1,2,1,6,6,6,5,6,1,2};
            for(int i=0,j=0; i<60; i+=4,j++){

                       //随即音符
                System.out.println(j);
                track.add(makeEvent(144, 0, whiteId[someMusic[j]-1] + base, 100, i));
                if(j==someMusic.length-1) {
                    j = 0;
                }
                System.out.println(j);

            }
            System.out.println("Here");
            player.setSequence(seq);
            player.setLoopCount(player.LOOP_CONTINUOUSLY);    //指定无穷的重复次数
            player.setTempoInBPM(120);
            player.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * MidiEvent是组合乐曲的指令,一连串的MidiEvent就好像是乐谱一样.
     * MidiEvent用来指示在何时执行什么操作,每个指令都必须包括该指令的执行时机.也就是说,乐声应该在那一拍发响.
     *
     * @param comd
     *                 chmod代表信息类型 144类型的信息代表NOTE ON表示打开 128代表NOTE OFF 表示关闭
     * @param chan
     *                 chan表示频道,每个频道代表不同的演奏者.
     *                 例如:一号频道是吉他,二号频道是Bass.或者可以像IronMaiden用3把不同音色的吉他编制
     * @param one
     *                 one表示音符,从0~127代表不同音高
     * @param two
     *                 two代表音道/音量,用多大的音道按下? 0几乎听不到,100算是差不多
     * @param tick
     * @return
     */
    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd,chan,one,two);
            //表示在tick拍启动a这个Message
            event = new MidiEvent(a, tick);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public static void main(String[] args) {
        MiniMusic mini = new MiniMusic();
        mini.play();

    }
}