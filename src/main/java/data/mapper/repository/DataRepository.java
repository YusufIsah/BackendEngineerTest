package data.mapper.repository;

import data.mapper.dto.DataMapper;
import data.mapper.entity.Record;
import util.QueryRules;
import util.StringUtil;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DataRepository {

    private List<DataMapper> dataMappers;

    public DataRepository() {
        this.dataMappers = new ArrayList<>();
    }

    /**
     * save data to dataMapper list as temporary storage
     * @param dataMapper {@link DataMapper}
     * @return {@link DataMapper}
     */
    public DataMapper save(DataMapper dataMapper) {
        dataMappers.add(dataMapper);
        return dataMapper;
    }

    /**
     * get filtered data
     * @param providerId Long
     * @param name String
     * @param age String
     * @param timestamp String
     * @return {@link Record}
     */
    public Record getFilteredData(Long providerId, String name, String age, String timestamp) {
        return performFilter(providerId, name, age, timestamp);
    }

    /**
     * perform filter operation on data
     * @param providerId Long
     * @param name String
     * @param age String
     * @param timestamp String
     * @return {@link Record}
     */
    private Record performFilter(Long providerId, String name, String age, String timestamp) {
        Record updateFilteredRecord = new Record();
        var regex = ":";
        String[] splitName = StringUtil.split(name, regex);
        String[] splitAge = StringUtil.split(age, regex);
        String[] splitTimestamp = StringUtil.split(timestamp, regex);

        dataMappers.stream().filter(dataMapper -> dataMapper.getProviderId().equals(providerId))
                .forEach(dataMapper -> dataMapper.getData()
                        .forEach(record -> updateRecordWithFilteredFields(updateFilteredRecord, record, splitName, splitAge, splitTimestamp)));
        return updateFilteredRecord;
    }

    /**
     * Update Record with filtered fields
     * @param updateFilteredRecord {@link Record}
     * @param record {@link Record}
     * @param splitName String
     * @param splitAge String
     * @param splitTimestamp String
     */
    private void updateRecordWithFilteredFields(Record updateFilteredRecord, Record record, String[] splitName, String[] splitAge, String[] splitTimestamp) {
        var isMatch = filterFields(record, splitName, splitAge, splitTimestamp);
        if (isMatch) {
            updateFilteredRecord.setName(record.getName());
            updateFilteredRecord.setAge(record.getAge());
            updateFilteredRecord.setTimestamp(record.getTimestamp());
        } else {
            updateFilteredRecord = new Record();
        }
    }

    /**
     * filtered based on the fields
     * @param record {@link Record}
     * @param name String
     * @param age String
     * @param timestamp String
     * @return boolean
     */
    private boolean filterFields(Record record, String[] name, String[] age, String[] timestamp) {
        var isName = false;
        var isAge = false;
        var isTimestamp = false;

        if (name[0].equals(QueryRules.EQC) && record.getName().toLowerCase().contains(name[1].toLowerCase())) {
            isName = true;
        }
        if (age[0].equals(QueryRules.EQ) && (Integer.parseInt(age[1]) == record.getAge())) {
            isAge = true;
        } else if (age[0].equals(QueryRules.LT) && (Integer.parseInt(age[1]) < record.getAge())) {
            isAge = true;
            ;
        } else if (age[0].equals(QueryRules.GT) && (Integer.parseInt(age[1]) > record.getAge())) {
            isAge = true;
            ;
        }

        if (timestamp[0].equals(QueryRules.EQ) && (Long.parseLong(timestamp[1]) == (record.getTimestamp()))) {
            isTimestamp = true;
        } else if (timestamp[0].equals(QueryRules.LT) && (Long.parseLong(timestamp[1]) < record.getTimestamp())) {
            isTimestamp = true;
            ;
        } else if (timestamp[0].equals(QueryRules.GT) && (Long.parseLong(timestamp[1]) > record.getTimestamp())) {
            isTimestamp = true;
            ;
        }
        return isName && isAge && isTimestamp;
    }

}
