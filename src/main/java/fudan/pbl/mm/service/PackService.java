package fudan.pbl.mm.service;

import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.Cell;
import fudan.pbl.mm.domain.Pack;
import fudan.pbl.mm.repository.CellInfoRepository;
import fudan.pbl.mm.repository.CellRepository;
import fudan.pbl.mm.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ResponseObject<?> collectCellInfo(String cellInfoType, Long cellId){
        Cell cell = cellRepository.findCellById(cellId);
        Pack pack = packRepository.findPackByCell(cell);
        pack.addToCellInfoSet(cellInfoRepository.findCellInfoByType(cellInfoType));
        packRepository.save(pack);
        return new ResponseObject<>(200, "success", null);
    }
}
