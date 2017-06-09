local function condition(size, flag)
	local result, filler = 'This is string'
	local filler = nil

	if flag == 0 then
		filler = ' zero'
	elseif flag == 1 then
		filler = ' one'
	else
		filler = ' none'
	end

	for idx = 1, size, 1 do
		result = result .. filler
	end

	return result
end

print(condition(3))
print(condition(1, 0))
print(condition(2, 1))
