package com.jxxhwl.wx.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jxxhwl.common.DatabaseContextHolder;
import com.jxxhwl.wx.entity.FaHuo;
import com.jxxhwl.wx.entity.QueryShouFa;
@Repository
public class GetFaDAOImplAdd extends JdbcDaoSupport {
	@Resource(name="dataSource2")
	public void setMyDataSource(DataSource ds ) {
		super.setDataSource(ds);
	}
	
	/**
	 * 
	 * @param queryShouFa
	 * @return
	 */
	public List<FaHuo> getFa(QueryShouFa queryShouFa) {
		List<FaHuo> list = new ArrayList<FaHuo>();
		String begin = queryShouFa.getBegin();
		String end = queryShouFa.getEnd();
		
		char[] c = queryShouFa.getCode().toCharArray();
		String descr = "";
		for (char cc : c){
			descr += ( cc + "%");
		}
		String sql = "select LEFT(convert(nvarchar(10),UpCardate,112),8) dd,shortname,NO,SF_QTY,LicensePlate,Driver,fangshi from (select '自有车' as fangshi,a.id,a.TrainNumber,a.UpCardate,a.Destination,a.shortname,a.NO,a.Type,a.SF_QTY,a.Carriers,CASE WHEN a.DB_DriverNamer ='' or a.DB_DriverNamer is null THEN a.DriverNamer else a.DB_DriverNamer end as Driver,a.LicensePlate, isnull(a.FreightName,'直发') as FreightName,a.Zzz_Type,a.IsTransfer,  CASE WHEN a.IsTransfer = 1 THEN NULL else a.Zzz_Type end as Zzz_t from(select t.id,t.TrainNumber,t.UpCardate,t.Destination,t.shortname,t.NO,t.Type,t.SF_QTY,c.id as Carriers,v.Owners as DriverNamer,v.LicensePlate,t1.DB_DriverNamer,ts.Zzz_Type,fl.FreightName,t.IsTransfer from TrainNumberToNo as t left join TrainNumber as t1 on t1.TrainNumber=t.TrainNumber left join Carriers as c on c.id =t1.Carriers left join Vehicle as v on v.id=t1.LicensePlate  left join Transfer_Station as ts on (SUBSTRING(ts.Destination,1,3)) = (SUBSTRING(t.Destination,1,3)) left join FreightList as fl on t.IsFreight=fl.id  where t.shortname like '%" + descr + "' and t1.Carriers=1 and LEFT(convert(nvarchar(10),t.UpCardate,112),8) between '" + begin + "' and '" + end + "' )  as a union select '承运商' as fangshi,b.id,b.TrainNumber,b.UpCardate,b.Destination,b.shortname,b.NO,b.Type,b.SF_QTY,b.Carriers,    CASE WHEN b.DB_DriverNamer ='' or b.DB_DriverNamer is null THEN b.DriverNamer else b.DB_DriverNamer end as Driver,b.LicensePlate, isnull(b.FreightName,'直发') as FreightName,b.Zzz_Type,b.IsTransfer,  CASE WHEN b.IsTransfer = 1 THEN NULL else b.Zzz_Type end as Zzz_t  from(    select t.id,t.TrainNumber,t.UpCardate,t.Destination,t.shortname,t.NO,t.Type,t.SF_QTY, c.id as Carriers,null as DriverNamer ,t1.LicensePlate,t1.DB_DriverNamer,ts.Zzz_Type,fl.FreightName,t.IsTransfer from TrainNumberToNo as t   left join TrainNumber as t1 on t1.TrainNumber=t.TrainNumber   left join Carriers as c on c.id =t1.Carriers left join Transfer_Station as ts on (SUBSTRING(ts.Destination,1,3)) = (SUBSTRING(t.Destination,1,3))    left join FreightList as fl on t.IsFreight=fl.id  where t.shortname like '%" + descr + "' and t1.Carriers<>1 and LEFT(convert(nvarchar(10),t.UpCardate,112),8)between '" + begin + "' and '" + end + "' ) as b union select '自提' as fangshi,id,TrainNumber,UpCardate,Destination,shortname,NO,Type,SF_QTY,'7' as Carriers,  null as DB_Driver, null as LicensePlate, null as Zzz_Type,null as FreightName,'' as IsTransfer,'' as Zzz_t  from TrainNumberToNo where shortname like '%" + descr + "' and  TrainNumber is null and LEFT(convert(nvarchar(10),UpCardate,112),8)between '" + begin + "' and '" + end + "') aa where type not like '教材%' ";
		list = getJdbcTemplate().query(sql, new RowMapper(){
			FaHuo fh = null;
			@Override
			public FaHuo mapRow(ResultSet rs, int rowNum) throws SQLException {
				fh = new FaHuo();
				fh.setShipdate(rs.getString(1));
				fh.setStation(rs.getString(2));
				fh.setNo(rs.getString(3));
				fh.setQty(rs.getInt(4));
				fh.setCarno(rs.getString(5));
				fh.setSiji(rs.getString(6));
				fh.setFangshi(rs.getString(7));
				return fh;
			}
		});
		
		return list;
	}
	
}
