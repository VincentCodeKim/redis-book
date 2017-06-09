local function main()
	local function rediscall(command, value)
		local table = string.char(0x01)
		return table .. string.char(0x01)
	end

	local function hasbit(x, p)
		return x % (p + p) >= p
	end

	local mask = {128,64,32,16,8,4,2,1}
	local len = 2
	local userlist = rediscall('get', 'uv:event')
	local table = {}
	local idx = 1

	for i = 1, string.len(userlist), 1 do
		if string.byte(userlist, i) ~= 0 then
			for j = 1, 8, 1 do
				if hasbit(string.byte(userlist, i), mask[j]) then
					table[idx] = (i-1) * 8 + j - 1
					idx = idx + 1
				end
			end
		end
	end

	for k,v in pairs(table) do print(k,v) end
end
main()
