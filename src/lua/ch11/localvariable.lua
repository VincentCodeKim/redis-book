local function myFunction()
	local localval1 = 100

	local function inner()
		local localval1 = 10
		return localval1
	end
	
	print(inner()) 
 	print(localval1)
	
	return localval1
end

myFunction()

