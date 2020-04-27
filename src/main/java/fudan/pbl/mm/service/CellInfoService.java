package fudan.pbl.mm.service;

import fudan.pbl.mm.domain.CellInfo;
import fudan.pbl.mm.repository.CellInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellInfoService {
    private CellInfoRepository cellInfoRepository;

    @Autowired
    public CellInfoService(CellInfoRepository cellInfoRepository){
        this.cellInfoRepository = cellInfoRepository;
    }

    public void initCellInfo(){
        CellInfo nerve = new CellInfo("nerve", "神经细胞是一种高度特化的细胞，是神经系统的基本结构和功能单位之一，它具" +
                "有感受刺激和传导兴奋的功能");
        CellInfo red = new CellInfo("red", "红细胞是血液中数量最多的一种血细胞，同时也是脊椎动物体内通过血液运送氧气的最主要的媒介，" +
                "同时还具有免疫功能");
        CellInfo platelet = new CellInfo("platelet", "血小板只存在于哺乳动物血液中，是哺乳动物血液中的有形成分之一。" +
                "血小板的主要功能是凝血和止血，修补破损的血管。");
        CellInfo skin = new CellInfo("skin", "上皮细胞是位于皮肤或腔道表层的细胞，上皮细胞的形状有扁平，柱状等。皮肤外层的上皮细胞" +
                "普遍角质化，有保护和吸收的作用。腔道中的上皮细胞多分化，有分泌，排泻和吸收等功能。");
        CellInfo stem = new CellInfo("stem", "干细胞是一类具有多向分化潜能和自我复制能力的原始的未分化细胞，" +
                "是形成哺乳类动物的各组织器官的原始细胞");
        CellInfo tCell = new CellInfo("T", "T细胞是淋巴细胞的主要组分，它具有多种生物学功能，" +
                "如直接杀伤靶细胞，辅助或抑制B细胞产生抗体，对特异性抗原和促有丝分裂原的应答反应以及产生细胞因子等，是身体中抵御疾病感染、肿瘤形成的英勇斗士。");
        CellInfo bCell = new CellInfo("B", "骨髓依赖性淋巴细胞简称B细胞，是由骨髓中的淋巴干细胞分化而来。与T淋巴细胞相比，它的体积略大。" +
                "这种淋巴细胞受抗原刺激后，会增殖分化出大量浆细胞。浆细胞可合成和分泌抗体并在血液中循环。");
        CellInfo eatCell = new CellInfo("phagocyte", "吞噬细胞对体内衰老死亡细胞和外来异物有吞噬和消化的功能，" +
                "细菌被吞噬在吞噬细胞内形成吞噬体；溶酶体与吞噬体融合成吞噬溶酶体；溶酶体中多种杀菌物质和水解酶将细菌杀死并消化；菌体残渣被排出细胞外。");
        cellInfoRepository.save(nerve);
        cellInfoRepository.save(red);
        cellInfoRepository.save(platelet);
        cellInfoRepository.save(skin);
        cellInfoRepository.save(stem);
        cellInfoRepository.save(tCell);
        cellInfoRepository.save(bCell);
        cellInfoRepository.save(eatCell);
    }
}
