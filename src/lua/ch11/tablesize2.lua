local function tablesize()
	local table1 = {}
	table1['kris'] = 100
	table1['niki'] = 1002
	table1['james'] = 200
	print ("table1의 크기", table.getn(table1))
	
	local table2 = {100, 1002, 200}
	print ("table2의 크기", table.maxn(table2))
end

tablesize()
