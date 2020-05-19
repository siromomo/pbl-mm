package fudan.pbl.mm.service;

import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.Cell;
import fudan.pbl.mm.domain.CellInfo;
import fudan.pbl.mm.domain.Pack;
import fudan.pbl.mm.repository.CellInfoRepository;
import fudan.pbl.mm.repository.CellRepository;
import fudan.pbl.mm.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PackService {
    private PackRepository packRepository;
    private CellRepository cellRepository;
    private CellInfoRepository cellInfoRepository;

    @Autowired
    public PackService(PackRepository packRepository, CellRepository cellRepository,
                       CellInfoRepository cellInfoRepository){
        this.packRepository = packRepository;
        this.cellRepository = cellRepository;
        this.cellInfoRepository = cellInfoRepository;
    }

    public ResponseObject<?> collectCellInfo(String cellInfoType, Long packId){
        Pack pack = packRepository.findPackById(packId);
        pack.addToCellInfoSet(cellInfoRepository.findCellInfoByType(cellInfoType));
        packRepository.save(pack);
        return new ResponseObject<>(200, "success", null);
    }

    public ResponseObject<Set<CellInfo>> getCellInfoHasCollected(Long packId){
        Pack pack = packRepository.findPackById(packId);
        return new ResponseObject<>(200, "success", pack.getCellInfoSet());
    }
}
